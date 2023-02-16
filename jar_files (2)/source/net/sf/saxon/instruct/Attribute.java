package net.sf.saxon.instruct;
import net.sf.saxon.Controller;
import net.sf.saxon.Err;
import net.sf.saxon.event.NoOpenStartTagException;
import net.sf.saxon.event.ReceiverOptions;
import net.sf.saxon.event.SequenceReceiver;
import net.sf.saxon.expr.*;
import net.sf.saxon.om.*;
import net.sf.saxon.pattern.NodeKindTest;
import net.sf.saxon.style.StandardNames;
import net.sf.saxon.type.ItemType;
import net.sf.saxon.type.SimpleType;
import net.sf.saxon.type.Type;
import net.sf.saxon.type.ValidationException;
import net.sf.saxon.value.QNameValue;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.xpath.DynamicError;
import net.sf.saxon.xpath.XPathException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.PrintStream;

/**
* An instruction derived from an xsl:attribute element in stylesheet, or from
 * an attribute constructor in XQuery
*/

public final class Attribute extends SimpleNodeConstructor {

    private Expression attributeName;
    private Expression namespace=null;
    private NamespaceResolver nsContext;
    private SimpleType schemaType;
    private int annotation;
    private int validationAction;
    private int options;

    /**
    * Construct an Attribute instruction
    * @param attributeName An expression to calculate the attribute name
    * @param namespace An expression to calculate the attribute namespace
    * @param nsContext a NamespaceContext object containing the static namespace context of the
    * stylesheet instruction
    * @param annotation Integer code identifying the type named in the <code>type</code> attribute
    * of the instruction - zero if the attribute was not present
    */

    public Attribute (  Expression attributeName,
                        Expression namespace,
                        NamespaceResolver nsContext,
                        int validationAction,
                        SimpleType schemaType,
                        int annotation ) {
        this.attributeName = attributeName;
        this.namespace = namespace;
        this.nsContext = nsContext;
        this.schemaType = schemaType;
        this.annotation = annotation;
        this.validationAction = validationAction;
        this.options = 0;
    }

    /**
     * Indicate that two attributes with the same name are not acceptable.
     * (This option is set in XQuery, but not in XSLT)
     */

    public void setRejectDuplicates() {
        this.options |= ReceiverOptions.REJECT_DUPLICATES;
    }

    /**
    * Get the name of this instruction
    */

    public int getInstructionNameCode() {
        return StandardNames.XSL_ATTRIBUTE;
    }

    public ItemType getItemType() {
        return NodeKindTest.ATTRIBUTE;
    }

    public int getCardinality() {
        return StaticProperty.EXACTLY_ONE;
    }

     public Expression simplify(StaticContext env) throws XPathException {
        attributeName = attributeName.simplify(env);
        if (namespace!=null) {
            namespace = namespace.simplify(env);
        }
        return super.simplify(env);
    }

    public void typeCheck(StaticContext env, ItemType contextItemType) throws XPathException {
        attributeName = attributeName.analyze(env, contextItemType);

        RoleLocator role =
                new RoleLocator(RoleLocator.INSTRUCTION, "attribute/name", 0);

        if (attributeName.getItemType() == Type.QNAME_TYPE) {
            // Can only happen in XQuery
            attributeName = TypeChecker.staticTypeCheck(attributeName,
                    SequenceType.SINGLE_ITEM, false, role, env);
        } else {
            attributeName = TypeChecker.staticTypeCheck(attributeName,
                    SequenceType.SINGLE_STRING, false, role, env);
        }

        if (namespace != null) {
            namespace.analyze(env, contextItemType);

            role = new RoleLocator(RoleLocator.INSTRUCTION, "attribute/namespace", 0);
            namespace = TypeChecker.staticTypeCheck(
                    namespace, SequenceType.SINGLE_STRING, false, role, env);
        }
    }

    public int getDependencies() {
        int dependencies = 0;
        dependencies |= attributeName.getDependencies();
        if (namespace != null) {
            dependencies |= namespace.getDependencies();
        }
        return dependencies | super.getDependencies();
    }

    public Iterator iterateSubExpressions() {
        ArrayList list = new ArrayList(10);
        if (children != null) {
            list.addAll(Arrays.asList(children));
        }
        if (select != null) {
            list.add(select);
        }
        if (separator != null && !(separator instanceof StringValue)) {
            list.add(separator);
        }
        list.add(attributeName);
        if (namespace != null) {
            list.add(namespace);
        }
        return list.iterator();
    }

   /**
     * Offer promotion for subexpressions. The offer will be accepted if the subexpression
     * is not dependent on the factors (e.g. the context item) identified in the PromotionOffer.
     * By default the offer is not accepted - this is appropriate in the case of simple expressions
     * such as constant values and variable references where promotion would give no performance
     * advantage. This method is always called at compile time.
     *
     * @param offer details of the offer, for example the offer to move
     *     expressions that don't depend on the context to an outer level in
     *     the containing expression
     * @exception XPathException if any error is detected
     */

    protected void promoteInst(PromotionOffer offer) throws XPathException {
        attributeName = attributeName.promote(offer);
        if (namespace != null) {
            namespace = namespace.promote(offer);
        }
        super.promoteInst(offer);
    }

    /**
    * Process this instruction
    * @param context the dynamic context of the transformation
    * @return a TailCall to be executed by the caller, always null for this instruction
    */

    public TailCall processLeavingTail(XPathContext context) throws XPathException
    {
        Controller controller = context.getController();
        int nameCode = evaluateNameCode(context);
        if (nameCode == -1) {
            return null;
        }
        SequenceReceiver out = context.getReceiver();
        int opt = options;
        int ann = annotation;

    	// we may need to change the namespace prefix if the one we chose is
    	// already in use with a different namespace URI: this is done behind the scenes
    	// by the Outputter

        String value = expandChildren(context).toString();
        if (schemaType != null) {
            // test whether the value actually conforms to the given type
            try {
                schemaType.validateContent(value, DummyNamespaceResolver.getInstance());
                if (schemaType.isNamespaceSensitive()) {
                    options |= ReceiverOptions.NEEDS_PREFIX_CHECK;
                }
            } catch (ValidationException err) {
                throw new ValidationException("Attribute value " + Err.wrap(value, Err.VALUE) +
                                               " does not match the required type " +
                                               schemaType.getDescription() + ". " +
                                               err.getMessage());
            }
        } else if (validationAction==Validation.STRICT ||
                validationAction==Validation.LAX) {
            long res = controller.getConfiguration().validateAttribute(nameCode,
                                                                         value,
                                                                         validationAction);
            ann = (int)(res & 0xffffffff);
            opt |= (int)(res >> 32);
        }
        try {
            out.attribute(nameCode, ann, value, locationId, opt);
        } catch (NoOpenStartTagException err) {
            //DynamicError e = new DynamicError("Cannot write an attribute node when no element start tag is open");
            err.setXPathContext(context);
            //e.setErrorCode("XT0410");
            context.getController().recoverableError(err);
        } catch (XPathException err) {
            throw dynamicError(this, err, context);
        }

        return null;
    }

    protected int evaluateNameCode(XPathContext context) throws XPathException, XPathException {
        Controller controller = context.getController();
        NamePool pool = controller.getNamePool();

        Item nameValue = attributeName.evaluateItem(context);

        String prefix = null;
        String localName = null;

        if (nameValue instanceof StringValue) {
            // this will always be the case in XSLT
            String rawName = nameValue.getStringValue();
            try {
                String[] parts = Name.getQNameParts(rawName);
                prefix = parts[0];
                localName = parts[1];
            } catch (QNameException err) {
                DynamicError err1 = new DynamicError("Invalid attribute name: " + rawName, this);
                err1.setErrorCode("XT0850");
                err1.setXPathContext(context);
                context.getController().recoverableError(err1);
                return -1;
            }
            if (rawName.equals("xmlns")) {
                if (namespace==null) {
                    DynamicError err = new DynamicError("Invalid attribute name: " + rawName, this);
                    err.setErrorCode("XT0850");
                    err.setXPathContext(context);
                    context.getController().recoverableError(err);
                    return -1;
                }
            }
            if (prefix.equals("xmlns")) {
                if (namespace==null) {
                    DynamicError err = new DynamicError("Invalid attribute name: " + rawName, this);
                    err.setErrorCode("XT0850");
                    err.setXPathContext(context);
                    context.getController().recoverableError(err);
                    return -1;
                } else {
                    // ignore the prefix "xmlns"
                    prefix = "";
                }
            }

        } else if (attributeName instanceof QNameValue) {
            // this is allowed in XQuery
            localName = ((QNameValue)nameValue).getLocalName();
            String namespaceURI = ((QNameValue)nameValue).getNamespaceURI();
            namespace = new StringValue(namespaceURI);
            if (namespaceURI.equals("")) {
                prefix = "";
            } else {
                // we need to allocate a prefix. Any one will do; if it's a duplicate,
                // a different one will be substituted
                prefix = pool.suggestPrefixForURI(namespaceURI);
                if (prefix == null) {
                    prefix = "nsq0";
                }
            }

        }

        String uri;

        if (namespace==null) {
        	if ("".equals(prefix)) {
        		uri = "";
        	} else {
                uri = nsContext.getURIForPrefix(prefix, false);
                if (uri==null) {
                    DynamicError err = new DynamicError("Undeclared prefix in attribute name: " + prefix, this);
                    err.setErrorCode("XT0860");
                    err.setXPathContext(context);
                    context.getController().recoverableError(err);
                    return -1;
      		    }
        	}

        } else {

            // generate a name using the supplied namespace URI

            uri = namespace.evaluateAsString(context);
            if ("".equals(uri)) {
                // there is a special rule for this case in the specification;
                // we force the attribute to go in the null namespace
                prefix = "";

            } else {
                // if a suggested prefix is given, use it; otherwise try to find a prefix
                // associated with this URI; if all else fails, invent one.
                if ("".equals(prefix)) {
                    prefix = pool.suggestPrefixForURI(uri);
                    if (prefix == null) {
                        prefix = "ns0";
                        // this will be replaced later if it is already in use
                    }
                }
            }
        }

        return pool.allocate(prefix, uri, localName);
    }

    /**
     * Display this instruction as an expression, for diagnostics
     */

    public void display(int level, NamePool pool, PrintStream out) {
        out.println(ExpressionTool.indent(level) + "attribute ");
        out.println(ExpressionTool.indent(level+1) + "name");
        attributeName.display(level+2, pool, out);
        super.display(level+1, pool, out);
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
