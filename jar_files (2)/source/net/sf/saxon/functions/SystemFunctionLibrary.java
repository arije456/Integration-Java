package net.sf.saxon.functions;

import net.sf.saxon.functions.StandardFunction;
import net.sf.saxon.functions.SystemFunction;
import net.sf.saxon.functions.XSLTFunction;
import net.sf.saxon.functions.FunctionLibrary;
import net.sf.saxon.om.NamespaceConstant;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.xpath.StaticError;
import net.sf.saxon.Configuration;
import net.sf.saxon.expr.Expression;

/**
 * The SystemFunctionLibrary represents the collection of functions in the fn: namespace. That is, the
 * functions defined in the "Functions and Operators" specification, optionally augmented by the additional
 * functions defined in XSLT.
 */

public class SystemFunctionLibrary implements FunctionLibrary {

    private boolean allowXSLTFunctions;
    //private Configuration config;

    /**
     * Create a SystemFunctionLibrary
     * @param allowXSLT true if XSLT additional functions (such as generate-id() and format-date()) are allowed.
     */

    public SystemFunctionLibrary(Configuration config, boolean allowXSLT) {
        //this.config = config;
        allowXSLTFunctions = allowXSLT;
    }

    /**
     * Test whether a system function with a given name and arity is available. This supports
     * the function-available() function in XSLT. This method may be called either at compile time
     * or at run time.
     * @param uri  The URI of the function name
     * @param local  The local part of the function name
     * @param arity The number of arguments. This is set to -1 in the case of the single-argument
     * function-available() function; in this case the method should return true if there is some
     * matching extension function, regardless of its arity.
     */

    public boolean isAvailable(int fingerprint, String uri, String local, int arity) {
        if (uri.equals(NamespaceConstant.FN)) {
            StandardFunction.Entry entry = StandardFunction.getFunction(local);
            if (entry == null) {
                return false;
            }
            return (arity == -1 ||
                    (arity >= entry.minArguments && arity <= entry.maxArguments));
        } else {
            return false;
        }
    }

    /**
     * Bind an extension function, given the URI and local parts of the function name,
     * and the list of expressions supplied as arguments. This method is called at compile
     * time.
     * @param uri  The URI of the function name
     * @param local  The local part of the function name
     * @param staticArgs  The expressions supplied statically in the function call. The intention is
     * that the static type of the arguments (obtainable via getItemType() and getCardinality() may
     * be used as part of the binding algorithm.
     * @return An object representing the extension function to be called, if one is found;
     * null if no extension function was found matching the required name and arity.
     * @throws net.sf.saxon.xpath.XPathException if a function is found with the required name and arity, but
     * the implementation of the function cannot be loaded or used; or if an error occurs
     * while searching for the function; or if this function library "owns" the namespace containing
     * the function call, but no function was found.
     */

    public Expression bind(int nameCode, String uri, String local, Expression[] staticArgs)
            throws XPathException {
        if (uri.equals(NamespaceConstant.FN)) {
            StandardFunction.Entry entry = StandardFunction.getFunction(local);
            if (entry == null) {
                throw new StaticError("Unknown system function " + local + "()");
            }
            Class functionClass = entry.implementationClass;
            SystemFunction f;
            try {
                f = (SystemFunction)functionClass.newInstance();
            } catch (Exception err) {
                throw new AssertionError("Failed to load system function: " + err.getMessage());
            }
            f.setDetails(entry);
            f.setFunctionNameCode(nameCode);
            f.setArguments(staticArgs);
            checkArgumentCount(staticArgs.length, entry.minArguments, entry.maxArguments, local);
            if (f instanceof XSLTFunction && !allowXSLTFunctions) {
                throw new StaticError("Cannot use the " + local + "() function in a non-XSLT context");
            }
            return f;
        } else {
            return null;
        }
    }

    /**
    * Check number of arguments. <BR>
    * A convenience routine for use in subclasses.
    * @param min the minimum number of arguments allowed
    * @param max the maximum number of arguments allowed
    * @return the actual number of arguments
    * @throws net.sf.saxon.xpath.XPathException if the number of arguments is out of range
    */

    private int checkArgumentCount(int numArgs, int min, int max, String local) throws XPathException {
        if (min==max && numArgs != min) {
            throw new StaticError("Function " + local + " must have "
                    + min + pluralArguments(min));
        }
        if (numArgs < min) {
            throw new StaticError("Function " + local + " must have at least "
                    + min + pluralArguments(min));
        }
        if (numArgs > max) {
            throw new StaticError("Function " + local + " must have no more than "
                    + max + pluralArguments(max));
        }
        return numArgs;
    }

    /**
    * Utility routine used in constructing error messages
    */

    private static String pluralArguments(int num) {
        if (num==1) return " argument";
        return " arguments";
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