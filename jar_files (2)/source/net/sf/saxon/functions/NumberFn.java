package net.sf.saxon.functions;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.StaticContext;
import net.sf.saxon.om.Item;
import net.sf.saxon.value.DoubleValue;
import net.sf.saxon.value.Value;
import net.sf.saxon.value.BooleanValue;
import net.sf.saxon.value.NumericValue;
import net.sf.saxon.type.Type;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.xpath.XPathException;

/**
 * Implements the XPath number() function
 */

public class NumberFn extends SystemFunction {

    /**
    * Simplify and validate.
    * This is a pure function so it can be simplified in advance if the arguments are known
    */

     public Expression simplify(StaticContext env) throws XPathException {
        useContextItemAsDefault();
        return simplifyArguments(env);
    }

    /**
    * Evaluate in a general context
    */

    public Item evaluateItem(XPathContext context) throws XPathException {
        Item arg0 = argument[0].evaluateItem(context);
        if (arg0==null) {
            return DoubleValue.NaN;
        }
        if (arg0 instanceof BooleanValue || arg0 instanceof NumericValue) {
            return ((AtomicValue)arg0).convert(Type.DOUBLE, context);
        }
        String s = arg0.getStringValue();
        try {
            return new DoubleValue(Value.stringToNumber(s));
        } catch (NumberFormatException e) {
            return DoubleValue.NaN;
        }
    }

    /**
     * Static method to perform the same conversion as the number() function. This is different from the
     * convert(Type.DOUBLE) in that it produces NaN rather than an error for non-numeric operands.
     */

    public static DoubleValue convert(AtomicValue value) {
        try {
            if (value==null) {
                return DoubleValue.NaN;
            }
            if (value instanceof BooleanValue || value instanceof NumericValue) {
                return (DoubleValue)value.convert(Type.DOUBLE, null);
            }
            String s = value.getStringValue();
            return new DoubleValue(Value.stringToNumber(s));
        } catch (NumberFormatException e) {
            return DoubleValue.NaN;
        } catch (XPathException e) {
            return DoubleValue.NaN;
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
// The Initial Developer of the Original Code is Michael H. Kay.
//
// Portions created by (your name) are Copyright (C) (your legal entity). All Rights Reserved.
//
// Contributor(s): none.
//
