package net.sf.saxon.functions;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.sort.DescendingComparer;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.value.NumericValue;
import net.sf.saxon.value.UntypedAtomicValue;
import net.sf.saxon.value.Value;
import net.sf.saxon.value.DoubleValue;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.Err;

import java.util.Comparator;

/**
* This class implements the min() and max() functions
*/

public class Minimax extends CollatingFunction {

    public final static int MIN = 2;
    public final static int MAX = 3;

    /**
    * Evaluate the function
    */

    public Item evaluateItem(XPathContext context) throws XPathException {

        Comparator collator = getAtomicComparer(1, context);

        if (operation == MAX) {
            collator = new DescendingComparer(collator);
        }

        SequenceIterator iter = argument[0].iterate(context);

        AtomicValue min = (AtomicValue)iter.next();
        if (min == null) return null;

        while (true) {
            AtomicValue test = (AtomicValue)iter.next();
            if (test==null) break;
            AtomicValue test2 = test;
            if (test instanceof UntypedAtomicValue) {
                try {
                    test2 = new DoubleValue(Value.stringToNumber(test.getStringValue()));
                } catch (NumberFormatException e) {
                    dynamicError("Failure converting " +
                                                     Err.wrap(test.getStringValue()) +
                                                     " to a number", context);
                }
            }
            if (test2 instanceof NumericValue &&
                    ((NumericValue)test2).isNaN()) {
                // if there's a NaN in the sequence, return NaN
                return test2;
            }
            try {
                if (collator.compare(test2, min) < 0) {
                    min = test2;
                }
            } catch (ClassCastException err) {
                typeError("Cannot compare " + min.getItemType() +
                                   " with " + test2.getItemType(), context);
                return null;
            }
        }
        return min;
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
