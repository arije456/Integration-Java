package net.sf.saxon.sql;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.RoleLocator;
import net.sf.saxon.expr.TypeChecker;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.instruct.Executable;
import net.sf.saxon.instruct.GeneralVariable;
import net.sf.saxon.instruct.TailCall;
import net.sf.saxon.instruct.InstructionDetails;
import net.sf.saxon.om.Name;
import net.sf.saxon.om.Navigator;
import net.sf.saxon.style.XSLGeneralVariable;
import net.sf.saxon.tree.AttributeCollection;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.Value;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.trace.InstructionInfo;
import net.sf.saxon.trace.Location;

import javax.xml.transform.TransformerConfigurationException;


/**
* An sql:column element in the stylesheet.
*/

public class SQLColumn extends XSLGeneralVariable {

    /**
    * Determine whether this node is an instruction.
    * @return false - it is not an instruction
    */

    public boolean isInstruction() {
        return false;
    }

    /**
    * Determine whether this type of element is allowed to contain a template-body
    * @return false: no, it may not contain a template-body
    */

    public boolean mayContainSequenceConstructor() {
        return false;
    }

    public void prepareAttributes() throws TransformerConfigurationException {

        getVariableFingerprint();

		AttributeCollection atts = getAttributeList();

		String selectAtt = null;
        String nameAtt = null;

		for (int a=0; a<atts.getLength(); a++) {
			String localName = atts.getLocalName(a);
			if (localName.equals("name")) {
        		nameAtt = atts.getValue(a).trim();
        	} else if (localName.equals("select")) {
        		selectAtt = atts.getValue(a);
        	} else {
        		checkUnknownAttribute(atts.getNameCode(a));
        	}
        }

        if (nameAtt==null) {
            reportAbsence("name");
        } else if (!Name.isQName(nameAtt)) {
            compileError("Column name must be a valid QName");
        }

        if (selectAtt!=null) {
            select = makeExpression(selectAtt);
        }

    }


    public void validate() throws TransformerConfigurationException {
        if (!(getParentNode() instanceof SQLInsert)) {
            compileError("parent node must be sql:insert");
        }
        select = typeCheck("select", select);
        try {
            RoleLocator role =
                new RoleLocator(RoleLocator.INSTRUCTION, "sql:column/select", 0);
            select = TypeChecker.staticTypeCheck(select,
                        SequenceType.SINGLE_ATOMIC,
                        false, role, getStaticContext());

        } catch (XPathException err) {
            compileError(err);
        }
    }

    public Expression compile(Executable exec) throws TransformerConfigurationException {
        ColumnInstruction inst = new ColumnInstruction();
        initializeInstruction(exec, inst);
        return inst;
    }

    public String getColumnName() {
        return Navigator.getAttributeValue(this, "", "name");
    }

    protected static class ColumnInstruction extends GeneralVariable {

        public ColumnInstruction() {}

        public InstructionInfo getInstructionInfo() {
            InstructionDetails details = (InstructionDetails)super.getInstructionInfo();
            details.setConstructType(Location.EXTENSION_INSTRUCTION);
            return details;
        }

        public TailCall processLeavingTail(XPathContext context) {
            return null;
        }

        /**
         * Evaluate the variable (method exists only to satisfy the interface)
         */

        public Value evaluateVariable(XPathContext context) throws XPathException {
            throw new UnsupportedOperationException();
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
