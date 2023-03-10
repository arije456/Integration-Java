package net.sf.saxon.tinytree;
import net.sf.saxon.om.AxisIteratorImpl;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.pattern.NodeTest;

/**
* This class supports both the child:: and following-sibling:: axes, which are
* identical except for the route to the first candidate node.
* It enumerates either the children or the following siblings of the specified node.
* In the case of children, the specified node must always
* be a node that has children: to ensure this, construct the enumeration
* using NodeInfo#getEnumeration()
*/

final class SiblingEnumeration extends AxisIteratorImpl {

    private TinyDocumentImpl document;
    private int nextNodeNr;
    private NodeTest test;
    private TinyNodeImpl startNode;
    private TinyNodeImpl parentNode;
    private boolean getChildren;
    private boolean needToAdvance = false;

    /**
     * Return an enumeration over children or siblings of the context node
     * @param doc The Document node containing the context node
     * @param node The context node, the start point for the iteration
     * @param nodeTest Test that the selected nodes must satisfy, or null indicating
     * that all nodes are selected
     * @param getChildren True if children of the context node are to be returned, false
     * if following siblings are required
     */

    protected SiblingEnumeration(TinyDocumentImpl doc, TinyNodeImpl node,
                              NodeTest nodeTest, boolean getChildren) {
        document = doc;
        test = nodeTest;
        startNode = node;
        this.getChildren = getChildren;
        if (getChildren) {          // child:: axis
            parentNode = node;

            // move to first child
            // ASSERT: we don't invoke this code unless the node has children
            nextNodeNr = node.nodeNr + 1;

        } else {                    // following-sibling:: axis
            parentNode = (TinyNodeImpl)node.getParent();
            if (parentNode == null) {
                nextNodeNr = -1;
            } else {

                // move to next sibling
                nextNodeNr = doc.next[node.nodeNr];
                if (nextNodeNr < node.nodeNr) {         // *O*
                    nextNodeNr = -1;                    // *O*
                }                                       // *O*
            }
        }

        // check if this matches the conditions
        if (nextNodeNr >= 0 && nodeTest != null) {
            if (!nodeTest.matches(document.nodeKind[nextNodeNr],
                                  document.nameCode[nextNodeNr],
                                  document.getElementAnnotation(nextNodeNr))) {
                needToAdvance = true;
            }
        }
    }

    public Item next() {
         // if needToAdvance == false, we are already on the correct node.
        if (needToAdvance) {
            final int thisNode = nextNodeNr;
            if (test==null) {
                nextNodeNr = document.next[nextNodeNr];
            } else {
                do {
                    nextNodeNr = document.next[nextNodeNr];
                } while ( nextNodeNr >= thisNode &&
                        !test.matches(document.nodeKind[nextNodeNr],
                                      document.nameCode[nextNodeNr],
                                      document.getElementAnnotation(nextNodeNr)));
            }

            if (nextNodeNr < thisNode) {    // indicates we've found an owner pointer
                nextNodeNr = -1;
                needToAdvance = false;
                return null;
            }
        }

        if (nextNodeNr == -1) {
            return null;
        }
        needToAdvance = true;
        position++;

        // if the caller only wants the atomized value, get it directly
        // in the case where it's an atomic value

        if (isAtomizing() && document.getElementAnnotation(nextNodeNr) == -1) {
            current = document.getUntypedAtomicValue(nextNodeNr);
            return current;
        } else {
            current = document.getNode(nextNodeNr);
            ((TinyNodeImpl)current).setParentNode(parentNode);
            return current;
        }
    }

    /**
    * Get another enumeration of the same nodes
    */

    public SequenceIterator getAnother() {
        return new SiblingEnumeration(document, startNode, test, getChildren);
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
