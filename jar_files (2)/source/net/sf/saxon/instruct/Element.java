package net.sf.saxon.instruct;
import net.sf.saxon.Configuration;
import net.sf.saxon.Controller;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.expr.*;
import net.sf.saxon.om.*;
import net.sf.saxon.style.StandardNames;
import net.sf.saxon.type.SchemaType;
import net.sf.saxon.type.ItemType;
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
* An instruction representing xsl:element element in the stylesheet.
*/

public class Element extends ElementCreator {

    private Expression elementName;
    private Expression namespace = null;
    private NamespaceResolver nsContext;
    private boolean allowNameAsQName;

    /**
     * Create an instruction that creates a new element node
     * @param elementName Expression that evaluates to produce the name of the
     * element node as a lexical QName
     * @param namespace Expression that evaluates to produce the namespace URI of
     * the element node. Set to null if the namespace is to be deduced from the prefix
     * of the elementName.
     * @param nsContext Saved copy of the static namespace context for the instruction.
     * Can be set to null if namespace is supplied.
     * @param useAttributeSets Array of attribute sets to be expanded
     * @param schemaType The required schema type for the content
     */
    public Element( Expression elementName,
                    Expression namespace,
                    NamespaceResolver nsContext,
                    AttributeSet[] useAttributeSets,
                    SchemaType schemaType,
                    int validation) {
        this.elementName = elementName;
        this.namespace = namespace;
        this.nsContext = nsContext;
        this.useAttributeSets = useAttributeSets;
        this.schemaType = schemaType;
        this.validation = validation;
    }

     public Expression simplify(StaticContext env) throws XPathException {
        elementName = elementName.simplify(env);
        if (namespace!=null) {
            namespace = namespace.simplify(env);
        }
        allowNameAsQName = (env.getConfiguration().getHostLanguage() ==Configuration.XQUERY);
        return super.simplify(env);
    }

    public Expression analyze(StaticContext env, ItemType contextItemType) throws XPathException {
        elementName = elementName.analyze(env, contextItemType);

        RoleLocator role =
                new RoleLocator(RoleLocator.INSTRUCTION, "element/name", 0);

        if (allowNameAsQName) {
            // Can only happen in XQuery
            elementName = TypeChecker.staticTypeCheck(
                    elementName, SequenceType.SINGLE_ITEM, false, role, env);
        } else {
            elementName = TypeChecker.staticTypeCheck(
                    elementName, SequenceType.SINGLE_STRING, false, role, env);
        }
        if (namespace != null) {
            namespace = namespace.analyze(env, contextItemType);

            role = new RoleLocator(RoleLocator.INSTRUCTION, "element/namespace", 0);
            namespace = TypeChecker.staticTypeCheck(
                    namespace, SequenceType.SINGLE_STRING, false, role, env);
        }
        super.analyze(env, contextItemType);
        return this;
    }


    public Iterator iterateSubExpressions() {
        ArrayList list = new ArrayList(8);
        if (children != null) {
            list.addAll(Arrays.asList(children));
        }
        list.add(elementName);
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
        elementName = elementName.promote(offer);
        if (namespace != null) {
            namespace = namespace.promote(offer);
        }
        super.promoteInst(offer);
    }

    /**
     * Callback from the superclass ElementCreator to get the nameCode
     * for the element name
     * @param context The evaluation context (not used)
     * @return the name code for the element name
     */

    protected int getNameCode(XPathContext context)
            throws XPathException, XPathException {

        Controller controller = context.getController();
        NamePool pool = controller.getNamePool();

        String prefix;
        String localName;
        String uri;

            // name needs to be evaluated at run-time
        Item nameValue = elementName.evaluateItem(context);
        if (nameValue instanceof StringValue) {
            // this will always be the case in XSLT
            String rawName = nameValue.getStringValue();
            try {
                String[] parts = Name.getQNameParts(rawName);
                prefix = parts[0];
                localName = parts[1];
            } catch (QNameException err) {
                DynamicError err1 = new DynamicError("Invalid element name. " + err.getMessage(), this);
                err1.setErrorCode("XT0820");
                err1.setXPathContext(context);
                context.getController().recoverableError(err1);
                return -1;
            }
        } else if (nameValue instanceof QNameValue && allowNameAsQName) {
            // this is allowed in XQuery
            localName = ((QNameValue)nameValue).getLocalName();
            uri = ((QNameValue)nameValue).getNamespaceURI();
            namespace = new StringValue(uri);
            // we need to allocate a prefix. Any one will do; if it's a duplicate,
            // a different one will be substituted
            prefix = pool.suggestPrefixForURI(uri);
            if (prefix == null) {
                prefix = "nsq0";
            }
        } else {
            DynamicError err = new DynamicError("Computed element name has incorrect type");
            err.setErrorCode("XT0820");
            err.setXPathContext(context);
            context.getController().recoverableError(err);
            return -1;
        }

        if (namespace==null) {
            uri = nsContext.getURIForPrefix(prefix, true);
            if (uri==null) {
                DynamicError err = new DynamicError("Undeclared prefix in element name: " + prefix, this);
                err.setErrorCode("XT0830");
                err.setXPathContext(context);
                context.getController().recoverableError(err);
                return -1;
  		    }

        } else {
            uri = namespace.evaluateAsString(context);
            if (uri.equals("")) {
                // there is a special rule for this case in the specification;
                // we force the element to go in the null namespace
                prefix = "";
            }
            if (prefix.equals("xmlns")) {
                // this isn't a legal prefix so we mustn't use it
                prefix = "x-xmlns";
            }
        }

        return pool.allocate(prefix, uri, localName);

    }

    /**
     * Callback to output namespace nodes for the new element.
     * @param context The execution context
     * @param out the Receiver where the namespace nodes are to be written
     * @throws XPathException
     */
    protected void outputNamespaceNodes(XPathContext context, Receiver out)
    throws XPathException {
        // do nothing
    }


    /**
    * Get the name of this instruction for diagnostic and tracing purposes
    */

    public int getInstructionNameCode() {
        return StandardNames.XSL_ELEMENT;
    }

    /**
     * Display this instruction as an expression, for diagnostics
     */

    public void display(int level, NamePool pool, PrintStream out) {
        out.println(ExpressionTool.indent(level) + "element ");
        out.println(ExpressionTool.indent(level+1) + "name");
        elementName.display(level+2, pool, out);
        if (children.length==0) {
            out.println(ExpressionTool.indent(level+1) + "empty content");
        } else {
            InstructionWithChildren.displayChildren(children, level+1, pool, out);
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
