package net.sf.saxon.functions;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.StaticContext;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.value.QNameValue;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.xpath.XPathException;

/**
* This class supports the name(), local-name(), and namespace-uri() functions
* from XPath 1.0, and also the XSLT generate-id() function
*/

public class NamePart extends SystemFunction {

    public final static int NAME = 0;
    public final static int LOCAL_NAME = 1;
    public final static int NAMESPACE_URI = 2;
    public final static int GENERATE_ID = 3;
    public final static int DOCUMENT_URI = 4;
    public final static int NODE_NAME = 6;

    /**
    * Simplify and validate.
    */

     public Expression simplify(StaticContext env) throws XPathException {
        useContextItemAsDefault();
        return simplifyArguments(env);
    }

    /**
    * Evaluate the function in a string context
    */

    public Item evaluateItem(XPathContext c) throws XPathException {
        NodeInfo node = (NodeInfo)argument[0].evaluateItem(c);
        if (node==null) {
            // All the functions that allow the argument to be an empty sequence
            // return an empty string, except for node-name()
            if (operation == NODE_NAME) {
                return null;
            } else {
                return StringValue.EMPTY_STRING;
            }
        }

        String s;
        switch (operation) {
            case NAME:
                s = node.getDisplayName();
                break;
            case LOCAL_NAME:
                s = node.getLocalPart();
                break;
            case NAMESPACE_URI:
                String uri = node.getURI();
                s = (uri==null ? "" : uri);
                        // null should no longer be returned, but the spec has changed, so it's
                        // better to be defensive
                break;
            case GENERATE_ID:
                s = node.generateId();
                break;
            case DOCUMENT_URI:
                s = node.getSystemId();
                break;
            case NODE_NAME:
                int nc = node.getNameCode();
                if (nc == -1) {
                    return null;
                }
                return new QNameValue(node.getNamePool(), nc);
            default:
                throw new UnsupportedOperationException("Unknown name operation");
        }
        return new StringValue(s);
    }

    /**
     * Test whether an expression is a call on the generate-id() function
     * @param exp the expression to be tested
     * @return true if exp is a call on generate-id(), else false
     */

    public static boolean isGenerateIdFunction(Expression exp) {
        return ((exp instanceof NamePart) && ((NamePart)exp).operation == GENERATE_ID);
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
