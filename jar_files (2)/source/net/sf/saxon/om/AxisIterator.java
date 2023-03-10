package net.sf.saxon.om;


/**
 * A SequenceIterator is used to iterate over a sequence. An AxisIterator
 * is a SequenceIterator that throws no exceptions.
 * Despite its name, an AxisIterator is not invariably used to find nodes
 * on an axis of a tree, though this is its most common use. For example, the
 * class ArrayIterator is also defined as an AxisIterator.
 */

public interface AxisIterator extends SequenceIterator {

    /**
     * Get the next item in the sequence. <BR>
     * @return the next Item. If there are no more nodes, return null.
     */

    public Item next();

    /**
     * Get the current item in the sequence.
     *
     * @return the current item, that is, the item most recently returned by
     *     next()
     */

    public Item current();

    /**
     * Get the current position
     *
     * @return the position of the current item (the item most recently
     *     returned by next()), starting at 1 for the first node
     */

    public int position();

    /**
     * Get another iterator over the same sequence of items, positioned at the
     * start of the sequence
     * @return a new iterator over the same sequence
     */

    public SequenceIterator getAnother();

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
