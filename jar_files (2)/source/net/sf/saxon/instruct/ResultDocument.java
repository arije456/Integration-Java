package net.sf.saxon.instruct;

import net.sf.saxon.Controller;
import net.sf.saxon.OutputURIResolver;
import net.sf.saxon.pattern.NoNodeTest;
import net.sf.saxon.event.SaxonOutputKeys;
import net.sf.saxon.event.SequenceReceiver;
import net.sf.saxon.event.StandardOutputResolver;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.ExpressionTool;
import net.sf.saxon.expr.PromotionOffer;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.om.*;
import net.sf.saxon.style.StandardNames;
import net.sf.saxon.type.SchemaType;
import net.sf.saxon.type.ItemType;
import net.sf.saxon.xpath.DynamicError;
import net.sf.saxon.xpath.XPathException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import java.io.PrintStream;

/**
 * An xsl:result-document (formerly saxon:output) element in the stylesheet. <BR>
 * The xsl:result-document element takes an attribute href="filename". The filename will
 * often contain parameters, e.g. {position()} to ensure that a different file is produced
 * for each element instance. <BR>
 * There is a further attribute "format" which determines the format of the
 * output file, it identifies the name of an xsl:output element containing the output
 * format details.
 */

public class ResultDocument extends InstructionWithChildren {

    private Expression href;
    private Properties outputProperties;
    private String baseURI;     // needed only for saxon:next-in-chain
    private int validationAction;
    private SchemaType schemaType;
    private HashMap serializationAttributes;
    private NamespaceResolver nsResolver;

    public ResultDocument(Properties outputProperties,
                          Expression href,
                          String baseURI,
                          int validationAction,
                          SchemaType schemaType,
                          HashMap serializationAttributes,
                          NamespaceResolver nsResolver) {
        this.outputProperties = outputProperties;
        this.href = href;
        this.baseURI = baseURI;
        this.validationAction = validationAction;
        this.schemaType = schemaType;
        this.serializationAttributes = serializationAttributes;
        this.nsResolver = nsResolver;
        for (Iterator it = serializationAttributes.values().iterator(); it.hasNext();) {
            adoptChildExpression((Expression) it.next());
        }
    }

    /**
     * Get the name of this instruction for diagnostic and tracing purposes
     *  (the string "xsl:result-document")
     */

    public int getInstructionNameCode() {
        return StandardNames.XSL_RESULT_DOCUMENT;
    }

    /**
     * Get the item type of the items returned by evaluating this instruction
     * @return the static item type of the instruction. This is empty: the result-document instruction
     * returns nothing.
     */

    public ItemType getItemType() {
        return NoNodeTest.getInstance();
    }

    /**
     * Handle promotion offers, that is, non-local tree rewrites.
     * @param offer The type of rewrite being offered
     * @throws XPathException
     */

    protected void promoteInst(PromotionOffer offer) throws XPathException {
        href = href.promote(offer);
    }

    /**
     * Get all the XPath expressions associated with this instruction
     * (in XSLT terms, the expression present on attributes of the instruction,
     * as distinct from the child instructions in a sequence construction)
     */

    public Iterator iterateSubExpressions() {
        ArrayList list = new ArrayList(6);
        if (children != null) {
            list.addAll(Arrays.asList(children));
        }
        if (href != null) {
            list.add(href);
        }
        for (Iterator it = serializationAttributes.values().iterator(); it.hasNext();) {
            list.add(it.next());
        }
        return list.iterator();
    }

    public TailCall processLeavingTail(XPathContext context) throws XPathException {
        Controller controller = context.getController();
        XPathContext c2 = context.newMinorContext();
        c2.setOrigin(this);

        Result result;
        OutputURIResolver resolver = null;

        if (href == null) {
            result = controller.getPrincipalResult();
        } else {
            try {
                resolver = controller.getOutputURIResolver();

                String hrefValue = href.evaluateAsString(context);
                result = resolver.resolve(hrefValue, controller.getPrincipalResultURI());
                if (result == null) {
                    resolver = StandardOutputResolver.getInstance();
                    result = resolver.resolve(hrefValue, controller.getPrincipalResultURI());
                }
            } catch (TransformerException e) {
                throw XPathException.wrap(e);
            }
        }

        boolean timing = controller.getConfiguration().isTiming();
        if (timing) {
            String dest = result.getSystemId();
            if (dest == null) {
                if (result instanceof StreamResult) {
                    dest = "anonymous output stream";
                } else if (result instanceof SAXResult) {
                    dest = "SAX2 ContentHandler";
                } else if (result instanceof DOMResult) {
                    dest = "DOM tree";
                } else {
                    dest = result.getClass().getName();
                }
            }
            System.err.println("Writing to " + dest);
        }

        Properties props = outputProperties;
        if (serializationAttributes.size() > 0) {
            props = new Properties(outputProperties);
            final NamePool namePool = context.getController().getNamePool();
            for (Iterator it = serializationAttributes.keySet().iterator(); it.hasNext();) {
                Integer key = (Integer) it.next();
                Expression exp = (Expression) serializationAttributes.get(key);
                String value = exp.evaluateAsString(context);
                try {
                    setSerializationProperty(props, key.intValue(), value, namePool, nsResolver);
                } catch (DynamicError e) {
                    e.setXPathContext(context);
                    e.setLocator(getSourceLocator());
                    throw e;
                }
            }
        }
        String nextInChain = outputProperties.getProperty(SaxonOutputKeys.NEXT_IN_CHAIN);
        if (nextInChain != null) {
            try {
                result = controller.prepareNextStylesheet(nextInChain, baseURI, result);
            } catch (TransformerException e) {
                throw XPathException.wrap(e);
            }
        }

        c2.changeOutputDestination(props,
                result,
                true,
                validationAction,
                schemaType);
        processChildren(c2);
        SequenceReceiver out = c2.getReceiver();
        out.close();
        if (resolver != null) {
            try {
                resolver.close(result);
            } catch (TransformerException e) {
                throw XPathException.wrap(e);
            }
        }
        return null;
    }

    /**
     * Validate a serialization property and add its value to a Properties collection
     */

    public static void setSerializationProperty(Properties details, int fp,
                                                String value, NamePool pool,
                                                NamespaceResolver nsResolver)
            throws XPathException {
        String lname = pool.getLocalName(fp);
        String uri = pool.getURI(fp);
        if (uri.equals("")) {
            if (lname.equals(StandardNames.METHOD)) {
                if (value.equals("xml") || value.equals("html") ||
                        value.equals("text") || value.equals("xhtml")) {
                    details.put(OutputKeys.METHOD, value);
                } else {
                    String[] parts;
                    try {
                        parts = Name.getQNameParts(value);
                        String prefix = parts[0];
                        if (prefix.equals("")) {
                            throw new DynamicError("method must be xml, html, xhtml, or text, or a prefixed name");
                        } else {
                            String muri = nsResolver.getURIForPrefix(prefix, false);
                            if (muri==null) {
                                throw new DynamicError("Namespace prefix '" + prefix + "' has not been declared");
                            }
                            details.put(OutputKeys.METHOD, '{' + muri + '}' + parts[1]);
                        }
                    } catch (QNameException e) {
                        throw new DynamicError("Invalid method name. " + e.getMessage());
                    }
                }
            } else

            if (lname.equals(StandardNames.OUTPUT_VERSION)) {
                details.put(OutputKeys.VERSION, value);
            } else

            if (lname.equals(StandardNames.INDENT)) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(OutputKeys.INDENT, value);
                } else {
                    throw new DynamicError("indent must be yes or no");
                }
            } else

            if (lname.equals(StandardNames.ENCODING)) {
                details.put(OutputKeys.ENCODING, value);
            } else

            if (lname.equals(StandardNames.MEDIA_TYPE)) {
                details.put(OutputKeys.MEDIA_TYPE, value);
            } else

            if (lname.equals(StandardNames.DOCTYPE_SYSTEM)) {
                details.put(OutputKeys.DOCTYPE_SYSTEM, value);
            } else

            if (lname.equals(StandardNames.DOCTYPE_PUBLIC)) {
                details.put(OutputKeys.DOCTYPE_PUBLIC, value);
            } else

            if (lname.equals(StandardNames.OMIT_XML_DECLARATION)) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(OutputKeys.OMIT_XML_DECLARATION, value);
                } else {
                    throw new DynamicError("omit-xml-declaration attribute must be yes or no");
                }
            } else

            if (lname.equals(StandardNames.STANDALONE)) {
                if (value.equals("yes") || value.equals("no") || value.equals("omit")) {
                    details.put(OutputKeys.STANDALONE, value);
                    throw new DynamicError("standalone attribute must be yes or no or omit");
                }
            } else

            if (lname.equals(StandardNames.CDATA_SECTION_ELEMENTS)) {
                String existing = details.getProperty(OutputKeys.CDATA_SECTION_ELEMENTS);
                if (existing == null) {
                    existing = "";
                }
                String s = "";
                StringTokenizer st = new StringTokenizer(value);
                while (st.hasMoreTokens()) {
                    String displayname = st.nextToken();
                    try {
                        String[] parts = Name.getQNameParts(displayname);
                        String muri = nsResolver.getURIForPrefix(parts[0], true);
                        if (muri==null) {
                            throw new DynamicError("Namespace prefix '" + parts[0] + "' has not been declared");
                        }
                        s += " {" + muri + '}' + parts[1];
                    } catch (QNameException err) {
                        throw new DynamicError("Invalid CDATA element name. " + err.getMessage());
                    }

                    details.put(OutputKeys.CDATA_SECTION_ELEMENTS, existing + s);
                }
            } else

            if (lname.equals(StandardNames.UNDECLARE_NAMESPACES)) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(SaxonOutputKeys.UNDECLARE_NAMESPACES, value);
                } else {
                    throw new DynamicError("undeclare-namespaces value must be yes or no");
                }
            } else

            if (lname.equals(StandardNames.INCLUDE_CONTENT_TYPE)) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(SaxonOutputKeys.INCLUDE_CONTENT_TYPE, value);
                } else {
                    throw new DynamicError("include-content-type attribute must be yes or no");
                }
            } else

            if (lname.equals(StandardNames.ESCAPE_URI_ATTRIBUTES)) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(SaxonOutputKeys.ESCAPE_URI_ATTRIBUTES, value);
                } else {
                    throw new DynamicError("escape-uri-attributes value must be yes or no");
                }
            }

        } else if (uri.equals(NamespaceConstant.SAXON)) {

            if (lname.equals("character-representation")) {
                details.put(SaxonOutputKeys.CHARACTER_REPRESENTATION, value);
            } else

            if (lname.equals("indent-spaces")) {
                try {
                    Integer.parseInt(value);
                    details.put(OutputKeys.INDENT, "yes");
                    details.put(SaxonOutputKeys.INDENT_SPACES, value);
                } catch (NumberFormatException err) {
                    throw new DynamicError("saxon:indent-spaces must be an integer");
                }
            } else

            if (lname.equals("next-in-chain")) {
                throw new DynamicError("saxon:next-in-chain value cannot be specified dynamically");
            } else

            if (lname.equals("byte-order-mark")) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(SaxonOutputKeys.BYTE_ORDER_MARK, value);
                } else {
                    throw new DynamicError("saxon:byte-order-mark value must be yes or no");
                }
            } else

            if (lname.equals("require-well-formed")) {
                if (value.equals("yes") || value.equals("no")) {
                    details.put(SaxonOutputKeys.REQUIRE_WELL_FORMED, value);
                } else {
                    throw new DynamicError("saxon:require-well-formed value must be yes or no");
                }
            }

        } else {

            // deal with user-defined attributes
            details.put('{' + uri + '}' + lname, value);
        }

    }

    /**
     * Diagnostic print of expression structure. The expression is written to the System.err
     * output stream
     *
     * @param level indentation level for this expression
     * @param out
     */

    public void display(int level, NamePool pool, PrintStream out) {
        out.println(ExpressionTool.indent(level) + "result-document");
        displayChildren(children, level + 1, pool, out);
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
// Additional Contributor(s): Brett Knights [brett@knightsofthenet.com]
//
