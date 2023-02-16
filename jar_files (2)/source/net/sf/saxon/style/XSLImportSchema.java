package net.sf.saxon.style;
import net.sf.saxon.Configuration;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.instruct.Executable;
import net.sf.saxon.tree.AttributeCollection;
import net.sf.saxon.type.SchemaException;

import javax.xml.transform.TransformerConfigurationException;


/**
* Compile-time representation of an xsl:import-schema declaration
 * in a stylesheet
*/

public class XSLImportSchema extends StyleElement {

    public void prepareAttributes() throws TransformerConfigurationException {

		AttributeCollection atts = getAttributeList();
        String href = null;
        String namespace = null;

		for (int a=0; a<atts.getLength(); a++) {
			int nc = atts.getNameCode(a);
			String f = getNamePool().getClarkName(nc);
            if (f==StandardNames.SCHEMA_LOCATION) {
        		href = atts.getValue(a).trim();
            } else if (f==StandardNames.NAMESPACE) {
                href = atts.getValue(a).trim();
        	} else {
        		checkUnknownAttribute(nc);
        	}
        }

        if ("".equals(namespace)) {
            compileError("The zero-length string is not a valid namespace URI. "+
                    "For a schema with no namspace, omit the namespace attribute");
        }
    }

    public void validate() throws TransformerConfigurationException {
        checkEmpty();
        checkTopLevel(null);
    }

    public void readSchema() throws SchemaException, TransformerConfigurationException {
        try {
            String schemaLoc = getAttributeValue(StandardNames.SCHEMA_LOCATION);
            if (schemaLoc != null) {
                schemaLoc = schemaLoc.trim();
            }
            String namespace = getAttributeValue(StandardNames.NAMESPACE);
            if (namespace==null) {
                namespace = "";
            } else {
                namespace = namespace.trim();
            }
            Configuration config = getPreparedStylesheet().getConfiguration();
            if (!config.isSchemaAware(Configuration.XSLT)) {
                compileError("To use xsl:import-schema, you need the schema-aware version of Saxon from http://www.saxonica.com/");
                return;
            }
            if (config.getSchema(namespace)==null) {
                if (schemaLoc == null) {
                    compileError("The schema-location attribute is required (no schema for this namespace is known)");
                    return;
                }
                namespace = config.readSchema(getBaseURI(), schemaLoc, namespace);
            }
            getPrincipalStylesheet().addImportedSchema(namespace);
        } catch (SchemaException err) {
            compileError(err.getMessage());
        } catch (TransformerConfigurationException err) {
            compileError(err.getMessage());
        }

    }


    public Expression compile(Executable exec) throws TransformerConfigurationException {
        return null;
        // No action. The effect of import-schema is compile-time only
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
