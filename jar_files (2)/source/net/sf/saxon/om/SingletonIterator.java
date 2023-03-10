package net.sf.saxon.om;

import net.sf.saxon.expr.LastPositionFinder;
import net.sf.saxon.expr.ReversibleIterator;


/**
* SingletonIterator: an iterator over a sequence of zero or one values
*/

public class SingletonIterator implements AxisIterator,
        ReversibleIterator, LastPositionFinder {

    private Item value;
    private boolean gone;

    /**
     * Private constructor: external classes should use the factory method
     * @param value the item to iterate over
     */

    private SingletonIterator(Item value) {
        this.value = value;
        gone = (value==null);
    }

   /**
    * Factory method.
    * @param item the item to iterate over
    * @return a SingletonIterator over the supplied item, or an EmptyIterator
    * if the supplied item is null.
    */

    public static AxisIterator makeIterator(Item item) {
       if (item==null) {
           return EmptyIterator.getInstance();
       } else {
           return new SingletonIterator(item);
       }
   }

    public Item next() {
        if (gone) {
            return null;
        }
        gone = true;
        return value;
    }

    public Item current() {
        return value;
    }

    public int position() {
        return 1;
    }

    public int getLastPosition() {
        return 1;
    }

    public SequenceIterator getAnother() {
        return new SingletonIterator(value);
    }

    /**
     * Indicate that any nodes returned in the sequence will be atomized. This
     * means that if it wishes to do so, the implementation can return the typed
     * values of the nodes rather than the nodes themselves. The implementation
     * is free to ignore this hint.
     * @param atomizing true if the caller of this iterator will atomize any
     * nodes that are returned, and is therefore willing to accept the typed
     * value of the nodes instead of the nodes themselves.
     */

    //public void setIsAtomizing(boolean atomizing) {}

    public SequenceIterator getReverseIterator() {
        return new SingletonIterator(value);
    }

    public Item getValue() {
        return value;
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
