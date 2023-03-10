package net.sf.saxon.value;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.type.AtomicType;
import net.sf.saxon.type.ItemType;
import net.sf.saxon.type.Type;
import net.sf.saxon.xpath.DynamicError;
import net.sf.saxon.xpath.XPathException;

import java.util.Comparator;


/**
* An Untyped Atomic value. This inherits from StringValue for implementation convenience, even
* though an untypedAtomic value is not a String in the data model type hierarchy.
*/

public class UntypedAtomicValue extends StringValue {

    public static final UntypedAtomicValue ZERO_LENGTH_UNTYPED =
            new UntypedAtomicValue("");

    // If the value is used once as a number, it's likely that it will be used
    // repeatedly as a number, so we cache the result of conversion

    DoubleValue doubleValue = null;

    /**
    * Constructor
    * @param value the String value. Null is taken as equivalent to "".
    */

    public UntypedAtomicValue(CharSequence value) {
        this.value = (value==null ? "" : value);
    }

    /**
    * Return the type of the expression
    * @return Type.UNTYPED_ATOMIC (always)
    */

    public ItemType getItemType() {
        return Type.UNTYPED_ATOMIC_TYPE;
    }

    /**
    * Convert to target data type
    */

    public AtomicValue convert(int requiredType, XPathContext context) throws XPathException {
        if (requiredType==Type.STRING) {
            return new StringValue(value);
        } else if (requiredType==Type.DOUBLE) {
            if (doubleValue==null) {
                doubleValue = (DoubleValue)super.convert(requiredType, context);
            }
            return doubleValue;
        } else {
            return super.convert(requiredType, context);
        }
    }

    /**
    * Compare an untypedAtomic value with another value, using a given collator to perform
    * any string comparisons. This works by converting the untypedAtomic value to the type
     * of the other operand, which is the correct behavior for operators like "=" and "!=",
     * but not for "eq" and "ne": in the latter case, the untypedAtomic value is converted
     * to a string and this method is therefore not used.
     * @return -1 if the this value is less than the other, 0 if they are equal, +1 if this
     * value is greater.
    */

    public int compareTo(Object other, Comparator collator) {
        if (other instanceof NumericValue) {
            if (doubleValue == null) {
                try {
                    doubleValue = (DoubleValue)convert(Type.DOUBLE, null);
                } catch (XPathException err) {
                throw new ClassCastException("Cannot convert untyped value " +
                        '\"' + getStringValue() + "\" to a double");
                }
            }
            return doubleValue.compareTo(other);
        } else if (other instanceof StringValue) {
                return collator.compare(getStringValue(), ((StringValue)other).getStringValue());
        } else if (other instanceof AtomicValue) {
            try {
                AtomicValue conv = convert((AtomicType)((Value)other).getItemType(), null);
                if (!(conv instanceof Comparable)) {
                    throw new DynamicError("Type " + ((Value)other).getItemType() + " is not ordered");
                }
                return ((Comparable)conv).compareTo(other);
            } catch (XPathException err) {
                throw new ClassCastException("Cannot convert untyped atomic value '" + getStringValue()
                            + "' to type " + ((Value)other).getItemType());
            }

        } else {
            // I'm not sure if we need this, but it does no harm
            return collator.compare(getStringValue(), other.toString());
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

