package net.sf.saxon.sort;
import java.util.Comparator;
import java.io.Serializable;


/**
 * A collating sequence that uses Unicode codepoint ordering
 */


// NOTE: ideally, this class would be a subclass of java.text.Collator, so it
// could be used anywhere a Collator can be used. However, it doesn't appear to
// be possible to subclass java.text.Collator, because this would require implementing
// getCollationKey(), and the CollationKey() object does not have a public constructor.

public class CodepointCollator implements Comparator, Serializable {

    public static String URI = "http://www.w3.org/2004/07/xpath-functions/collation/codepoint";

    private static CodepointCollator theInstance = new CodepointCollator();

    public static CodepointCollator getInstance() {
        return theInstance;
    }

    /**
    * Compare two string objects.
    * @return <0 if a<b, 0 if a=b, >0 if a>b
    * @throws ClassCastException if the objects are of the wrong type for this Comparer
    */

    public int compare(Object a, Object b) {
        return ((String)a).compareTo((String)b);
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
// The Initial Developer of the Original Code is Michael H. Kay
//
// Portions created by (your name) are Copyright (C) (your legal entity). All Rights Reserved.
//
// Contributor(s): none
//