package net.sf.saxon.expr;
import net.sf.saxon.value.Value;
import net.sf.saxon.xpath.XPathException;

/**
* Binding is a interface used to represent the run-time properties and methods
* associated with a variable: specifically, methods to get the name, type, and value
* of the variable.
*/

public interface Binding  {

    /**
    * Evaluate the variable
    */

    public Value evaluateVariable(XPathContext context) throws XPathException;

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
