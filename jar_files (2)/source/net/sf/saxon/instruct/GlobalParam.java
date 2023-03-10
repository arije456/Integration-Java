package net.sf.saxon.instruct;
import net.sf.saxon.Controller;
import net.sf.saxon.style.StandardNames;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.value.Value;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.xpath.DynamicError;

/**
* The compiled form of a global xsl:param element in the stylesheet or an
* external variable declared in the prolog of a Query. <br>
* The xsl:param element in XSLT has mandatory attribute name and optional attribute select. It can also
* be specified as required="yes" or required="no". In standard XQuery external variables are always required,
* and no default value can be specified; but Saxon provides an extension pragma that allows a query
* to specify a default.
*/

public final class GlobalParam extends GlobalVariable {

    /**
    * Get the name of this instruction for diagnostic and tracing purposes
    */

    public int getInstructionNameCode() {
        return StandardNames.XSL_PARAM;
    }

    /**
    * Evaluate the variable
    */

    public Value evaluateVariable(XPathContext context) throws XPathException {
        Controller controller = context.getController();
        Bindery b = controller.getBindery();
        boolean wasSupplied = b.useGlobalParameter(getVariableFingerprint(), this);
        if (wasSupplied) {
            return b.getGlobalVariableValue(this);
        } else {
            if (isRequiredParam()) {
                DynamicError e = new DynamicError("No value supplied for required parameter $" +
                        context.getController().getNamePool().getDisplayName(getVariableFingerprint()));
                e.setXPathContext(context);
                e.setLocator(getSourceLocator());
                e.setErrorCode("XT0050");
                throw e;
                // TODO: we aren't reporting this error if the required parameter is never referenced
            }

            // This is the first reference to a global variable; try to evaluate it now.
            // But first set a flag to stop looping. This flag is set in the Bindery because
            // the VariableReference itself can be used by multiple threads simultaneously

            try {
                b.setExecuting(this, true);
                //XPathContext c2 = context.newCleanContext();
                Value value = getSelectValue(context);
                b.defineGlobalVariable(this, value);
                b.setExecuting(this, false);
                return value;

            } catch (XPathException err) {
                if (err instanceof XPathException.Circularity) {
                    DynamicError e = new DynamicError("Circular definition of parameter " + getVariableName());
                    e.setXPathContext(context);
                    e.setErrorCode("XT0640");
                    throw e;
                } else {
                    throw err;
                }
            }
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
