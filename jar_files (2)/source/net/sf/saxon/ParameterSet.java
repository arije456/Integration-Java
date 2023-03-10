package net.sf.saxon;
import net.sf.saxon.value.Value;

/**
 * A ParameterSet is a set of parameters supplied when calling a template.
 * It is a collection of name-value pairs, the names being represented by numeric references
 * to the NamePool
 */

public class ParameterSet
{
	private int[] keys = new int[10];
    private Value[] values = new Value[10];
    private int used = 0;

    /** An empty parameter set (one that contains no parameters)
     */
    public final static ParameterSet EMPTY_PARAMETER_SET = new ParameterSet();

    /**
     * Create an empty parameter set
     */

    public ParameterSet() {}

    /**
     * Create a parameter set as a copy of an existing parameter set
     */

    public ParameterSet(ParameterSet existing) {
        for (int i=0; i<existing.used; i++) {
            put(existing.keys[i], existing.values[i]);
        }
    }

    /**
     * Add a parameter to the ParameterSet
     *
     * @param fingerprint The fingerprint of the parameter name.
     * @param value The value of the parameter
     */

    public void put (int fingerprint, Value value) {
        for (int i=0; i<used; i++) {
            if (keys[i]==fingerprint) {
                values[i]=value;
                return;
            }
        }
        if (used+1 > keys.length) {
        	int[] newkeys = new int[used*2];
            Value[] newvalues = new Value[used*2];
            System.arraycopy(values, 0, newvalues, 0, used);
            System.arraycopy(keys, 0, newkeys, 0, used);
            values = newvalues;
            keys = newkeys;
        }
        keys[used] = fingerprint;
        values[used++] = value;
    }

    /**
     * Get a parameter
     *
     * @param fingerprint The fingerprint of the name.
     * @return The value of the parameter, or null if not defined
     */

    public Value get (int fingerprint) {
        for (int i=0; i<used; i++) {
            if (keys[i]==fingerprint) {
                return values[i];
            }
        }
        return null;
    }

    /**
     * Clear all values
     */

    public void clear() {
        used = 0;
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