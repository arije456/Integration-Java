package net.sf.saxon.type;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NamePool;

import java.io.Serializable;


/**
 * An implementation of ItemType that matches any item (node or atomic value)
*/

public class AnyItemType implements ItemType, Serializable {

    private AnyItemType(){};

    private static AnyItemType theInstance = new AnyItemType();

    /**
     * Factory method to get the singleton instance
     */

    public static AnyItemType getInstance() {
        return theInstance;
    }


    /**
     * Test whether a given item conforms to this type
     * @param item The item to be tested
     * @return true if the item is an instance of this type; false otherwise
    */

    public boolean matchesItem(Item item) {
        return true;
    }

    public ItemType getSuperType() {
        return null;
    }

    /**
     * Get the primitive item type corresponding to this item type. For item(),
     * this is Type.ITEM. For node(), it is Type.NODE. For specific node kinds,
     * it is the value representing the node kind, for example Type.ELEMENT.
     * For anyAtomicValue it is Type.ATOMIC_VALUE. For numeric it is Type.NUMBER.
     * For other atomic types it is the primitive type as defined in XML Schema,
     * except that INTEGER is considered to be a primitive type.
     */

    public ItemType getPrimitiveItemType() {
        return this;
    }

    public int getPrimitiveType() {
        return Type.ITEM;
    }

    public AtomicType getAtomizedItemType() {
        return Type.ANY_ATOMIC_TYPE;
    }

    public String toString() {
        return "item()";
    }

    public String toString(NamePool pool) {
        return "item()";
    }

    /**
     * Returns a hash code value for the object.
     */

    public int hashCode() {
        return "AnyItemType".hashCode();
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
