package net.sf.saxon.expr;
import net.sf.saxon.Configuration;
import net.sf.saxon.Controller;
import net.sf.saxon.ParameterSet;
import net.sf.saxon.instruct.*;
import net.sf.saxon.om.AxisIterator;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.SingletonIterator;
import net.sf.saxon.sort.GroupIterator;
import net.sf.saxon.trace.InstructionInfoProvider;
import net.sf.saxon.trans.Mode;
import net.sf.saxon.value.Value;
import net.sf.saxon.xpath.XPathException;

/**
 * This class represents a "major context" in which an XPath expression is evaluated:
 * a "major context" object allows all aspects of the dynamic context to change, whereas
 * a "minor context" only allows changes to the focus and the destination for push output.
*/

// TODO: could refine further to create a lighter-weight context object for XQuery

public class XPathContextMajor extends XPathContextMinor {

    private StackFrame stackFrame = null;
    private ParameterSet localParameters = null;
    private ParameterSet tunnelParameters = null;
    private Mode currentMode = null;
    private Template currentTemplate = null;
    private GroupIterator currentGroupIterator = null;
    private RegexIterator currentRegexIterator = null;

    /**
    * Constructor should only be called by the Controller,
    * which acts as a XPathContext factory.
    */

    public XPathContextMajor(Controller c) {
        controller = c;
        stackFrame = StackFrame.EMPTY;
        origin = controller;
    }

    /**
    * Private Constructor
    */

    private XPathContextMajor() {
    }

    /**
    * Constructor for use in free-standing Java applications.
    * Must be used with care, since functions dependent on a Controller
    * will not be available.
    */

    public XPathContextMajor(Item item, Configuration config) {
        controller = new Controller(config);
        controller.setExecutable(new Executable());
        AxisIterator iter = SingletonIterator.makeIterator(item);
        iter.next();
        currentIterator = iter;
        origin = controller;
    }

    /**
    * Construct a new context as a copy of another. The new context is effectively added
    * to the top of a stack, and contains a pointer to the previous context
    */

    public XPathContextMajor newContext() {
        XPathContextMajor c = new XPathContextMajor();
        c.controller = controller;
        c.currentIterator = currentIterator;
        c.stackFrame = stackFrame;
        c.localParameters = localParameters;
        c.tunnelParameters = tunnelParameters;
        c.last = last;
        c.currentReceiver = currentReceiver;
        c.isTemporaryDestination = isTemporaryDestination;
        c.currentMode = currentMode;
        c.currentTemplate = currentTemplate;
        c.currentGroupIterator = currentGroupIterator;
        c.currentRegexIterator = currentRegexIterator;
        c.caller = this;
        return c;
    }

    public static XPathContextMajor newContext(XPathContextMinor p) {
        XPathContextMajor c = new XPathContextMajor();
        c.controller = p.getController();
        c.currentIterator = p.getCurrentIterator();
        c.stackFrame = p.getStackFrame();
        c.localParameters = p.getLocalParameters();
        c.tunnelParameters = p.getTunnelParameters();
        c.last = p.last;
        c.currentReceiver = p.currentReceiver;
        c.isTemporaryDestination = p.isTemporaryDestination;
        c.currentMode = p.getCurrentMode();
        c.currentTemplate = p.getCurrentTemplate();
        c.currentGroupIterator = p.getCurrentGroupIterator();
        c.currentRegexIterator = p.getCurrentRegexIterator();
        c.caller = p;
        return c;
    }

    /**
     * Get the local parameters for the current template call.
     * @return the supplied parameters
     */

    public ParameterSet getLocalParameters() {
        return localParameters;
    }

    /**
     * Set the local parameters for the current template call.
     * @param localParameters the supplied parameters
     */

    public void setLocalParameters(ParameterSet localParameters) {
        this.localParameters = localParameters;
    }

    /**
     * Get the tunnel parameters for the current template call.
     * @return the supplied tunnel parameters
     */

    public ParameterSet getTunnelParameters() {
        return tunnelParameters;
    }

    /**
     * Set the tunnel parameters for the current template call.
     * @param tunnelParameters the supplied tunnel parameters
     */

    public void setTunnelParameters(ParameterSet tunnelParameters) {
        this.tunnelParameters = tunnelParameters;
    }

    /**
     * Set the creating expression (for use in diagnostics). The origin is generally set to "this" by the
     * object that creates the new context. It's up to the debugger to determine whether this information
     * is useful. The object will either be an {@link InstructionInfoProvider}, allowing information
     * about the calling instruction to be obtained, or null.
     */

    public void setOrigin(InstructionInfoProvider expr) {
        origin = expr;
    }

    /**
     * Get a reference to the local stack frame for variables. Note that it's
     * the caller's job to make a local copy of this. This is used for creating
     * a Closure containing a retained copy of the variables for delayed evaluation.
     * @return array of variables.
     */

    public StackFrame getStackFrame() {
        return stackFrame;
    }


    /**
     * Set the local stack frame. This method is used when creating a Closure to support
     * delayed evaluation of expressions. The "stack frame" is actually on the Java heap, which
     * means it can survive function returns and the like.
     */

    public void setStackFrame(SlotManager map, Value[] variables) {
        stackFrame = new StackFrame(map, variables);
        if (map != null && variables.length != map.getNumberOfVariables()) {
            stackFrame.slots = new Value[map.getNumberOfVariables()];
            System.arraycopy(variables, 0, stackFrame.slots, 0, variables.length);
        }
    }

    /**
     * Create a new stack frame for local variables, using the supplied SlotManager to
     * define the allocation of slots to individual variables
     * @param map the SlotManager for the new stack frame
     */
    public void openStackFrame(SlotManager map) {
        stackFrame = new StackFrame(map, new Value[map.getNumberOfVariables()]);
    }

    /**
     * Create a new stack frame large enough to hold a given number of local variables,
     * for which no stack frame map is available. THis is used in particular when evaluating
     * match patterns of template rules.
     * @param numberOfVariables The number of local variables to be accommodated.
     */

    public void openStackFrame(int numberOfVariables) {
        stackFrame = new StackFrame(null, new Value[numberOfVariables]);
    }

    /**
     * Get the value of a local variable, identified by its slot number
     */

    public Value evaluateLocalVariable(int slotnumber) {
        return stackFrame.slots[slotnumber];
    }

    /**
     * Set the value of a local variable, identified by its slot number
     */

    public void setLocalVariable(int slotnumber, Value value) {
        stackFrame.slots[slotnumber] = value;
    }


    /**
     * Set the current mode.
     * @param mode the new current mode
     */

    public void setCurrentMode(Mode mode) {
        currentMode = mode;
    }

    /**
     * Get the current mode.
     * @return the current mode
     */

    public Mode getCurrentMode() {
        return currentMode;
    }

    /**
     * Set the current template. This is used to support xsl:apply-imports. The caller
     * is responsible for remembering the previous current template and resetting it
     * after use.
     *
     * @param template the current template
     */

    public void setCurrentTemplate(Template template) {
        currentTemplate = template;
    }

    /**
     * Get the current template. This is used to support xsl:apply-imports
     *
     * @return the current template
     */

    public Template getCurrentTemplate() {
        return currentTemplate;
    }

    /**
     * Set the current grouping iterator. This supports the current-group() and
     * current-grouping-key() functions in XSLT 2.0
     * @param collection the new current GroupIterator
     */

    public void setCurrentGroupIterator(GroupIterator collection) {
        currentGroupIterator = collection;
    }

    /**
     * Get the current group iterator. This supports the current-group() and
     * current-grouping-key() functions in XSLT 2.0
     * @return the current grouped collection
     */

    public GroupIterator getCurrentGroupIterator() {
        return currentGroupIterator;
    }

    /**
     * Set the current regex iterator. This supports the functionality of the regex-group()
     * function in XSLT 2.0.
     * @param currentRegexIterator the current regex iterator
     */

    public void setCurrentRegexIterator(RegexIterator currentRegexIterator) {
        this.currentRegexIterator = currentRegexIterator;
    }

    /**
     * Get the current regex iterator. This supports the functionality of the regex-group()
     * function in XSLT 2.0.
     * @return the current regular expressions iterator
     */

    public RegexIterator getCurrentRegexIterator() {
        return currentRegexIterator;
    }

    /**
    * Use local parameter. This is called when a local xsl:param element is processed.
    * If a parameter of the relevant name was supplied, it is bound to the xsl:param element.
    * Otherwise the method returns false, so the xsl:param default will be evaluated
    * @param fingerprint    The fingerprint of the parameter name
    * @param binding        The XSLParam element to bind its value to
    * @param isTunnel      True if a tunnel parameter is required, else false
    * @return true if a parameter of this name was supplied, false if not
    */

    public boolean useLocalParameter(int fingerprint,
                                     LocalParam binding,
                                     boolean isTunnel) throws XPathException {

        ParameterSet params = (isTunnel ? tunnelParameters : localParameters);
    	if (params==null) return false;
    	Value val = params.get(fingerprint);
        stackFrame.slots[binding.getSlotNumber()] = val;
        return (val != null);
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
