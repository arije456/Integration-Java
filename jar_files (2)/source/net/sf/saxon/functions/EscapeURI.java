package net.sf.saxon.functions;
import net.sf.saxon.charcode.UnicodeCharacterSet;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.Item;
import net.sf.saxon.value.BooleanValue;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.xpath.XPathException;

public class EscapeURI extends SystemFunction {

    /**
    * Evaluate in a general context
    */

    public Item evaluateItem(XPathContext c) throws XPathException {
        return new StringValue(asString(c));
    }


    private static String allHexDigits = "0123456789abcdefABCDEF";

    private String asString(XPathContext context) throws XPathException {
        String s = argument[0].evaluateItem(context).getStringValue();
        AtomicValue av2 = (AtomicValue)argument[1].evaluateItem(context);
        boolean escapeReserved = ((BooleanValue)av2.getPrimitiveValue()).getBooleanValue();
        String specials;
        StringBuffer sb = new StringBuffer(s.length());
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if ((c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9')) {
                sb.append(c);
            } else if (c<=0x20 || c>=0x7f) {
                escape(c, ((i+1)<s.length() ? s.charAt(i+1) : ' '), sb);
            } else if (c=='%') {
                // escape a % sign unless it's followed by two hex digits
                if (((i+2) < s.length()) && allHexDigits.indexOf(s.charAt(i+1))>=0
                                            && allHexDigits.indexOf(s.charAt(i+2))>=0) {
                    sb.append(c);
                } else {
                    escape(c, ' ', sb);
                }
            } else if (escapeReserved) {
                if ("-_.!~*'()".indexOf(c)>=0) {
                    sb.append(c);
                } else {
                    escape(c, ' ', sb);
                }
            } else {
                if ("-_.!~*'();/?:@&=+$,#[]".indexOf(c)>=0) {
                    sb.append(c);
                } else {
                    escape(c, ' ', sb);
                }
            }
        }
        return sb.toString();
    }

    private final static String hex = "0123456789ABCDEF";

    private void escape(char c, char c2, StringBuffer sb) {
        byte[] array = new byte[4];
        int used = UnicodeCharacterSet.getUTF8Encoding(c, c2, array);
        for (int b=0; b<used; b++) {
            int v = (array[b]>=0 ? array[b] : 256 + array[b]);
            sb.append('%');
            sb.append(hex.charAt(v/16));
            sb.append(hex.charAt(v%16));
        }
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
