package net.sf.saxon.style;
import net.sf.saxon.event.SaxonOutputKeys;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.instruct.Executable;
import net.sf.saxon.om.Name;
import net.sf.saxon.om.NamespaceConstant;
import net.sf.saxon.om.NamespaceException;
import net.sf.saxon.om.QNameException;
import net.sf.saxon.tree.AttributeCollection;
import net.sf.saxon.xpath.XPathException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

/**
* An xsl:output element in the stylesheet.
*/

public class XSLOutput extends StyleElement {

    private int fingerprint = -1;
    private String method = null;
    private String version = null;
    private String indent = null;
    private String encoding = null;
    private String mediaType = null;
    private String doctypeSystem = null;
    private String doctypePublic = null;
    private String omitDeclaration = null;
    private String standalone = null;
    private String cdataElements = null;
    private String includeContentType = null;
    private String nextInChain = null;
    private String representation = null;
    private String indentSpaces = null;
    private String byteOrderMark = null;
    private String escapeURIAttributes = null;
    private String requireWellFormed = null;
    private String undeclareNamespaces = null;
    private String useCharacterMaps = null;
    private HashMap userAttributes = null;

    //Emitter handler = null;

    public void prepareAttributes() throws TransformerConfigurationException {
		AttributeCollection atts = getAttributeList();
		String nameAtt = null;

        for (int a=0; a<atts.getLength(); a++) {
			int nc = atts.getNameCode(a);
			String f = getNamePool().getClarkName(nc);
			if (f==StandardNames.NAME) {
        		nameAtt = atts.getValue(a).trim();
			} else if (f==StandardNames.METHOD) {
        		method = atts.getValue(a).trim();
        	} else if (f==StandardNames.VERSION) {
        		version = atts.getValue(a).trim();
        	} else if (f==StandardNames.ENCODING) {
        		encoding = atts.getValue(a).trim();
        	} else if (f==StandardNames.OMIT_XML_DECLARATION) {
        		omitDeclaration = atts.getValue(a).trim();
        	} else if (f==StandardNames.STANDALONE) {
        		standalone = atts.getValue(a).trim();
        	} else if (f==StandardNames.DOCTYPE_PUBLIC) {
        		doctypePublic = atts.getValue(a).trim();
        	} else if (f==StandardNames.DOCTYPE_SYSTEM) {
        		doctypeSystem = atts.getValue(a).trim();
        	} else if (f==StandardNames.CDATA_SECTION_ELEMENTS) {
        		cdataElements = atts.getValue(a);
        	} else if (f==StandardNames.INDENT) {
        		indent = atts.getValue(a).trim();
        	} else if (f==StandardNames.MEDIA_TYPE) {
        		mediaType = atts.getValue(a).trim();
        	} else if (f==StandardNames.INCLUDE_CONTENT_TYPE) {
        		includeContentType = atts.getValue(a).trim();
        	} else if (f==StandardNames.ESCAPE_URI_ATTRIBUTES) {
        		escapeURIAttributes = atts.getValue(a).trim();
            } else if (f==StandardNames.USE_CHARACTER_MAPS) {
        		useCharacterMaps = atts.getValue(a);
            } else if (f==StandardNames.UNDECLARE_NAMESPACES) {
        		undeclareNamespaces = atts.getValue(a);
            } else if (f==StandardNames.SAXON_CHARACTER_REPRESENTATION) {
        		representation = atts.getValue(a).trim();
        	} else if (f==StandardNames.SAXON_INDENT_SPACES) {
        		indentSpaces = atts.getValue(a).trim();
        	} else if (f==StandardNames.SAXON_NEXT_IN_CHAIN) {
        		nextInChain = atts.getValue(a).trim();
            } else if (f==StandardNames.SAXON_BYTE_ORDER_MARK) {
                byteOrderMark = atts.getValue(a).trim();
            } else if (f==StandardNames.SAXON_REQUIRE_WELL_FORMED) {
                requireWellFormed = atts.getValue(a).trim();
        	} else {
        	    String attributeURI = getNamePool().getURI(nc);
        	    if ("".equals(attributeURI) ||
        	            NamespaceConstant.XSLT.equals(attributeURI) ||
        	            NamespaceConstant.SAXON.equals(attributeURI)) {
        		    checkUnknownAttribute(nc);
        		} else {
        		    String name = '{' + attributeURI + '}' + atts.getLocalName(a);
        		    if (userAttributes==null) {
        		        userAttributes = new HashMap(5);
        		    }
        		    userAttributes.put(name, atts.getValue(a));
        		}
        	}
        }
        if (nameAtt!=null) {
            try {
                fingerprint = makeNameCode(nameAtt.trim()) & 0xfffff;
            } catch (NamespaceException err) {
                compileError(err.getMessage());
            } catch (XPathException err) {
                compileError(err.getMessage());
            }
        }
    }

    /**
     * Get the name of the xsl:output declaration
     * @return the name, as a namepool fingerprint; or -1 for an unnamed output declaration
     */

    public int getOutputFingerprint() {
        return fingerprint;
    }

    public void validate() throws TransformerConfigurationException {
        checkTopLevel(null);
        checkEmpty();
    }

    public Expression compile(Executable exec) {
        return null;
    }


    /**
    * Validate the properties,
    * and return the values as additions to a supplied Properties object.
    */

    protected void gatherOutputProperties(Properties details)
    throws TransformerConfigurationException {

        if (method != null) {
            if (method.equals("xml") || method.equals("html") ||
                    method.equals("text") || method.equals("xhtml"))  {
                details.put(OutputKeys.METHOD, method);
            } else {
                String[] parts;
                try {
                    parts = Name.getQNameParts(method);
                    String prefix = parts[0];
                    if (prefix.equals("")) {
                        compileError("method must be xml, html, xhtml, or text, or a prefixed name");
                    } else {
                        String uri = getURIForPrefix(prefix, false);
                        details.put(OutputKeys.METHOD, '{' + uri + '}' + parts[1] );
                    }
                } catch (QNameException e) {
                    compileError("Invalid method name. " + e.getMessage());
                } catch (NamespaceException err) {
                    compileError(err.getMessage());
                }
            }
        }

        if (version != null) {
            details.put(OutputKeys.VERSION, version);
        }

        if (indent != null) {
            if (indent.equals("yes") || indent.equals("no")) {
                details.put(OutputKeys.INDENT, indent);
            } else {
                compileError("indent must be yes or no");
            }
        }

        if (indentSpaces != null) {
            try {
                Integer.parseInt(indentSpaces);
                details.put(OutputKeys.INDENT, "yes");
                details.put(SaxonOutputKeys.INDENT_SPACES, indentSpaces);
            } catch (NumberFormatException err) {
                compileError("saxon:indent-spaces must be an integer");
            }
        }

        if (encoding != null) {
            details.put(OutputKeys.ENCODING, encoding);
        }

        if (mediaType != null) {
            details.put(OutputKeys.MEDIA_TYPE, mediaType);
        }

        if (doctypeSystem != null) {
            details.put(OutputKeys.DOCTYPE_SYSTEM, doctypeSystem);
        }

        if (doctypePublic != null) {
            details.put(OutputKeys.DOCTYPE_PUBLIC, doctypePublic);
        }

        if (omitDeclaration != null) {
            if (omitDeclaration.equals("yes") || omitDeclaration.equals("no")) {
                details.put(OutputKeys.OMIT_XML_DECLARATION, omitDeclaration);
            } else {
                compileError("omit-xml-declaration attribute must be yes or no");
            }
        }

        if (standalone != null) {
            if (standalone.equals("yes") || standalone.equals("no")) {
                details.put(OutputKeys.STANDALONE, standalone);
            } else if (standalone.equals("omit")) {
                // ignore
            } else {
                compileError("standalone attribute must be yes or no");
            }
        }

        if (cdataElements != null) {
            String existing = details.getProperty(OutputKeys.CDATA_SECTION_ELEMENTS);
            if (existing==null) {
                existing = "";
            }
            String s = "";
            StringTokenizer st = new StringTokenizer(cdataElements);
            while (st.hasMoreTokens()) {
                String displayname = st.nextToken();
                try {
                    String[] parts = Name.getQNameParts(displayname);
                    String uri = getURIForPrefix(parts[0], true);
                    s += " {" + uri + '}' + parts[1];
                } catch (QNameException err) {
                    compileError("Invalid CDATA element name. " + err.getMessage());
                } catch (NamespaceException err) {
                    compileError(err.getMessage());
                }

                details.put(OutputKeys.CDATA_SECTION_ELEMENTS, existing+s);
            }
        }

        if (undeclareNamespaces != null) {
            if (undeclareNamespaces.equals("yes") || undeclareNamespaces.equals("no")) {
                details.put(SaxonOutputKeys.UNDECLARE_NAMESPACES, undeclareNamespaces);
            } else {
                compileError("undeclare-namespaces value must be yes or no");
            }
        }

        if (useCharacterMaps != null) {
            XSLStylesheet principal = getPrincipalStylesheet();
            String existing = details.getProperty(SaxonOutputKeys.USE_CHARACTER_MAPS);
            if (existing==null) {
                existing = "";
            }
            String s = "";
            StringTokenizer st = new StringTokenizer(useCharacterMaps);
            while (st.hasMoreTokens()) {
                String displayname = st.nextToken();
                try {
                    String[] parts = Name.getQNameParts(displayname);
                    String uri = getURIForPrefix(parts[0], false);
                    int nameCode = getTargetNamePool().allocate(parts[0], uri, parts[1]);
                    XSLCharacterMap ref =
                            principal.getCharacterMap(nameCode & 0xfffff);
                    if (ref == null) {
                        compileError("No character-map named '" + displayname + "' has been defined");
                    }
                    s += " {" + uri + '}' + parts[1];
                } catch (QNameException err) {
                    compileError("Invalid character-map name. " + err.getMessage());
                } catch (NamespaceException err) {
                    compileError(err.getMessage());
                }

                details.put(SaxonOutputKeys.USE_CHARACTER_MAPS, existing+s);
            }
        }

        if (representation != null) {
            details.put(SaxonOutputKeys.CHARACTER_REPRESENTATION, representation);
        }

        if (includeContentType != null) {
            if (includeContentType.equals("yes") || includeContentType.equals("no")) {
                details.put(SaxonOutputKeys.INCLUDE_CONTENT_TYPE, includeContentType);
            } else {
                compileError("include-content-type attribute must be yes or no");
            }
        }

        if (escapeURIAttributes != null) {
            if (escapeURIAttributes.equals("yes") || escapeURIAttributes.equals("no")) {
                details.put(SaxonOutputKeys.ESCAPE_URI_ATTRIBUTES, escapeURIAttributes);
            } else {
                compileError("escape-uri-attributes value must be yes or no");
            }
        }

        if (nextInChain != null) {
            details.put(SaxonOutputKeys.NEXT_IN_CHAIN, nextInChain);
            details.put(SaxonOutputKeys.NEXT_IN_CHAIN_BASE_URI, getSystemId());
        }

        if (byteOrderMark != null) {
            if (byteOrderMark.equals("yes") || byteOrderMark.equals("no")) {
                details.put(SaxonOutputKeys.BYTE_ORDER_MARK, byteOrderMark);
            } else {
                compileError("saxon:byte-order-mark value must be yes or no");
            }
        }

        if (requireWellFormed != null) {
            if (requireWellFormed.equals("yes") || requireWellFormed.equals("no")) {
                details.put(SaxonOutputKeys.REQUIRE_WELL_FORMED, requireWellFormed);
            } else {
                compileError("saxon:require-well-formed value must be yes or no");
            }
        }

        // deal with user-defined attributes

        if (userAttributes!=null) {
            Iterator iter = userAttributes.keySet().iterator();
            while (iter.hasNext()) {
                String attName = (String)iter.next();
                String data = (String)userAttributes.get(attName);
                details.put(attName, data);
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
