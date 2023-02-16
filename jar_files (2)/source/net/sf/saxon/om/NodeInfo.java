package net.sf.saxon.om;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.pattern.NodeTest;

import javax.xml.transform.Source;
import net.sf.saxon.xpath.XPathException;

/**
 * A node in the XML parse tree representing an XML element, character content, or attribute.
 * This is the top class in the interface hierarchy for nodes; see NodeImpl for the implementation
 * hierarchy.
 *
 * @author Michael H. Kay
 */

public interface NodeInfo extends Source, Item {

    /**
     * Get the kind of node. This will be a value such as Type.ELEMENT or Type.ATTRIBUTE
     *
     * @return an integer identifying the kind of node. These integer values are the
     * same as those used in the DOM
     * @see net.sf.saxon.type.Type
     */

    public int getNodeKind();

    /**
     * Determine whether this is the same node as another node.
     * Note: a.isSameNodeInfo(b) if and only if generateId(a)==generateId(b).
     * This method has the same semantics as isSameNode() in DOM Level 3, but
     * works on Saxon NodeInfo objects rather than DOM Node objects.
     * @param other the node to be compared with this node
     * @return true if this NodeInfo object and the supplied NodeInfo object represent
     *      the same node in the tree.
     */

    public boolean isSameNodeInfo(NodeInfo other);

    /**
     * Get the System ID for the node.
     *
     * @return the System Identifier of the entity in the source document
     * containing the node, or null if not known. Note this is not the
     * same as the base URI: the base URI can be modified by xml:base, but
     * the system ID cannot.
     */

    public String getSystemId();

    /**
     * Get the Base URI for the node, that is, the URI used for resolving a relative URI contained
     * in the node. This will be the same as the System ID unless xml:base has been used.
     *
     * @return the base URI of the node
     */

    public String getBaseURI();

    /**
     * Get line number
     *
     * @return the line number of the node in its original source document; or
     *      -1 if not available
     */

    public int getLineNumber();

    /**
     * Determine the relative position of this node and another node, in document order.
     * The other node will always be in the same document.
     *
     * @param other The other node, whose position is to be compared with this
     *      node
     * @return -1 if this node precedes the other node, +1 if it follows the
     *     other node, or 0 if they are the same node. (In this case,
     *     isSameNode() will always return true, and the two nodes will
     *     produce the same result for generateId())
     */

    public int compareOrder(NodeInfo other);

    /**
     * Return the string value of the node. The interpretation of this depends on the type
     * of node. For an element it is the accumulated character content of the element,
     * including descendant elements.
     *
     * @return the string value of the node
     */

    public String getStringValue();

	/**
	 * Get name code. The name code is a coded form of the node name: two nodes
	 * with the same name code have the same namespace URI, the same local name,
	 * and the same prefix. By masking the name code with &0xfffff, you get a
	 * fingerprint: two nodes with the same fingerprint have the same local name
	 * and namespace URI.
	 *
	 * @return an integer name code, which may be used to obtain the actual node
	 *     name from the name pool
	 * @see net.sf.saxon.om.NamePool#allocate allocate
	 * @see net.sf.saxon.om.NamePool#getFingerprint getFingerprint
	 */

	public int getNameCode();

	/**
	 * Get fingerprint. The fingerprint is a coded form of the expanded name
	 * of the node: two nodes
	 * with the same name code have the same namespace URI and the same local name.
	 * A fingerprint of -1 should be returned for a node with no name.
	 *
	 * @return an integer fingerprint; two nodes with the same fingerprint have
	 *     the same expanded QName
	 */

	public int getFingerprint();

    /**
     * Get the local part of the name of this node. This is the name after the ":" if any.
     *
     * @return the local part of the name. For an unnamed node, returns "". Unlike the DOM
     * interface, this returns the full name in the case of a non-namespaced name.
     */

    public String getLocalPart();

    /**
     * Get the URI part of the name of this node. This is the URI corresponding to the
     * prefix, or the URI of the default namespace if appropriate.
     *
     * @return The URI of the namespace of this node. For an unnamed node,
     *     or for a node with an empty prefix, return an empty
     *     string.
     */

    public String getURI();

    /**
     * Get the display name of this node. For elements and attributes this is [prefix:]localname.
     * For unnamed nodes, it is an empty string.
     *
     * @return The display name of this node. For a node with no name, return
     *     an empty string.
     */

    public String getDisplayName();

   /**
     * Get the NamePool that holds the namecode for this node
     * @return the namepool
     */

    public NamePool getNamePool();

    /**
     * Get the type annotation of this node, if any.
     * Returns -1 for kinds of nodes that have no annotation, and for elements annotated as
     * untyped, and attributes annotated as untypedAtomic.
     *
     * @return the type annotation of the node.
     * @see net.sf.saxon.type.Type
     */

    public int getTypeAnnotation();

    /**
     * Get the NodeInfo object representing the parent of this node
     *
     * @return the parent of this node; null if this node has no parent
     */

    public NodeInfo getParent();

    /**
     * Return an iteration over all the nodes reached by the given axis from this node
     *
     * @exception UnsupportedOperationException if the namespace axis is
     *     requested and this axis is not supported for this implementation.
     * @param axisNumber an integer identifying the axis; one of the constants
     *      defined in class net.sf.saxon.om.Axis
     * @return an AxisIterator that scans the nodes reached by the axis in
     *     turn.
     * @see net.sf.saxon.om.Axis
     */

    public AxisIterator iterateAxis(byte axisNumber);


    /**
     * Return an iteration over all the nodes reached by the given axis from this node
     * that match a given NodeTest
     *
     * @exception UnsupportedOperationException if the namespace axis is
     *     requested and this axis is not supported for this implementation.
     * @param axisNumber an integer identifying the axis; one of the constants
     *      defined in class net.sf.saxon.om.Axis
     * @param nodeTest A pattern to be matched by the returned nodes; nodes
     * that do not match this pattern are not included in the result
     * @return a NodeEnumeration that scans the nodes reached by the axis in
     *     turn.
     * @see net.sf.saxon.om.Axis
     */

    public AxisIterator iterateAxis(byte axisNumber, NodeTest nodeTest);

    /**
     * Get the value of a given attribute of this node
     *
     * @param fingerprint The fingerprint of the attribute name
     * @return the attribute value if it exists or null if not
     */

    public String getAttributeValue(int fingerprint);

    /**
     * Get the root node of the tree containing this node
     *
     * @return the NodeInfo representing the top-level ancestor of this node.
     *     This will not necessarily be a document node
     */

    public NodeInfo getRoot();

    /**
     * Get the root node, if it is a document node.
     *
     * @return the DocumentInfo representing the containing document. If this
     *     node is part of a tree that does not have a document node as its
     *     root, return null.
     */

    public DocumentInfo getDocumentRoot();

    /**
     * Determine whether the node has any children. <br />
     * Note: the result is equivalent to <br />
     * getEnumeration(Axis.CHILD, AnyNodeTest.getInstance()).hasNext()
     *
     * @return True if the node has one or more children
     */

    public boolean hasChildNodes();

    /**
     * Get a character string that uniquely identifies this node.
     * Note: a.isSameNode(b) if and only if generateId(a)==generateId(b)
     *
     * @return a string that uniquely identifies this node, across all
     *     documents. (Changed in Saxon 7.5. Previously this method returned
     *     an id that was unique within the current document, and the calling
     *     code prepended a document id).
     */

    public String generateId();

    /**
     * Get the document number of the document containing this node. For a free-standing
     * orphan node, just return the hashcode.
     */

    public int getDocumentNumber();

    /**
     * Copy this node to a given outputter
     *
     * @exception XPathException
     * @param out the Receiver to which the node should be copied
     * @param whichNamespaces in the case of an element, controls
     *     which namespace nodes should be copied. Values are NO_NAMESPACES,
     *     LOCAL_NAMESPACES, ALL_NAMESPACES
     * @param copyAnnotations indicates whether the type annotations
     *     of element and attribute nodes should be copied
     * @param locationId Identifies the location of the instruction
     *     that requested this copy. Pass zero if no other information is available
     */

    public void copy(Receiver out, int whichNamespaces, boolean copyAnnotations, int locationId) throws XPathException;

    int NO_NAMESPACES = 0;
    int LOCAL_NAMESPACES = 1;
    int ALL_NAMESPACES = 2;

    /**
     * Output all namespace nodes associated with this element. Does nothing if
     * the node is not an element.
     *
     * @param out The relevant outputter
     * @param includeAncestors True if namespaces declared on ancestor
     *     elements must be output; false if it is known that these are
     *     already on the result tree
     */

    public void outputNamespaceNodes(Receiver out, boolean includeAncestors)
        throws XPathException;

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
