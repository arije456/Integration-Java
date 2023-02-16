package net.sf.saxon.functions;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.MappingFunction;
import net.sf.saxon.expr.StaticContext;
import net.sf.saxon.om.EmptyIterator;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.value.*;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.xpath.DynamicError;
import net.sf.saxon.xpath.StaticError;
import net.sf.saxon.type.RegexTranslator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
* This class implements the tokenize() function for regular expression matching. This returns a
* sequence of strings representing the unmatched substrings: the separators which match the
* regular expression are not returned.
*/

public class Tokenize extends SystemFunction implements MappingFunction {

    private Pattern regexp;

    /**
    * Simplify and validate.
    * This is a pure function so it can be simplified in advance if the arguments are known
    */

     public Expression simplify(StaticContext env) throws XPathException {
        Expression e = simplifyArguments(env);

        // compile the regular expression once if possible
        if (!(e instanceof Value)) {
            regexp = Matches.tryToCompile(argument, 1, 2);
            // check that it's not a pattern that matches ""
            if (regexp != null && regexp.matcher("").matches()) {
                throw new StaticError(
                        "The regular expression must not be one that matches a zero-length string");
            }
        }

        return e;
    }

    /**
    * Iterate over the results of the function
    */

    public SequenceIterator iterate(XPathContext c) throws XPathException {
        AtomicValue sv = (AtomicValue)argument[0].evaluateItem(c);
        if (sv==null) return EmptyIterator.getInstance();
        String input = sv.getStringValue();

        Pattern re = regexp;
        if (re == null) {

            sv = (AtomicValue)argument[1].evaluateItem(c);
            String pattern = sv.getStringValue();

            String flags;
            if (argument.length==2) {
                flags = "";
            } else {
                sv = (AtomicValue)argument[2].evaluateItem(c);
                flags = sv.getStringValue();
            }

            try {
                String javaRegex = RegexTranslator.translate(pattern, true);
                re = Pattern.compile(javaRegex, Matches.setFlags(flags));
            } catch (RegexTranslator.RegexSyntaxException err) {
                throw new DynamicError(err);
            } catch (PatternSyntaxException err) {
                throw new DynamicError(err);
            }

            // check that it's not a pattern that matches ""
            if (re.matcher("").matches()) {
                throw new StaticError(
                        "The regular expression must not be one that matches a zero-length string");
            }

        }
        return new TokenIterator(input, re);
    }

    /**
     * Mapping function to map a sequence of Unicode codepoints to a sequence
     * of strings
     */

    public Object map(Item item, XPathContext context, Object info) throws XPathException {
        long next = ((NumericValue)item).longValue();
        if (next < 65536) {
            return new StringValue("" + (char)next);
        } else {  // output a surrogate pair
            //To compute the numeric value of the character corresponding to a surrogate
            //pair, use this formula (all numbers are hex):
            //(FirstChar - D800) * 400 + (SecondChar - DC00) + 10000
            next -= 65536;
            StringBuffer sb = new StringBuffer(2);
            sb.append((char)((next / 1024) + 55296));
            sb.append((char)((next % 1024) + 56320));
            return new StringValue(sb);
        }
    }


    /**
    * Inner class TokenIterator
    */

    public static class TokenIterator implements SequenceIterator {

        private String input;
        private Pattern pattern;
        private Matcher matcher;
        private String current;
        private int position = 0;
        private int prevEnd = 0;


        /**
        * Construct a TokenIterator.
        */

        public TokenIterator (String input, Pattern pattern) {
            this.input = input;
            this.pattern = pattern;
            matcher = pattern.matcher(input);
            prevEnd = 0;
        }

        public Item next() {
            if (prevEnd < 0) {
                return null;
            }

            if (matcher.find()) {
                current = input.substring(prevEnd, matcher.start());
                prevEnd = matcher.end();
            } else {
                current = input.substring(prevEnd);
                prevEnd = -1;
            }
            position++;
            return new StringValue(current);
        }

        public Item current() {
            return new StringValue(current);
        }

        public int position() {
            return position;
        }

        public SequenceIterator getAnother() {
            return new TokenIterator(input, pattern);
        }


    }

    /**
     * Simple command-line interface for testing.
     * @param args (1) the string to be tokenized (2) the regular expression
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        String in = args[0];
        String[] out = Pattern.compile(args[1]).split(in, 0);
        System.out.println("results");
        for (int i=0; i<out.length; i++) {
            System.out.println("[" + out[i] + "]");
        }
        System.out.println("end results");
    }



}



//
// The contents of this file are subject to the Mozilla Public License Version 1.0 (the "License");
// you may not use this file except in compliance with the License. You may obtain a copy of the
// License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied.
// See the License for the specific language governing rights and limitations under the License.
//
// The Original Code is: all this file.
//
// The Initial Developer of the Original Code is Michael H. Kay
//
// Portions created by (your name) are Copyright (C) (your legal entity). All Rights Reserved.
//
// Contributor(s): none.
//
