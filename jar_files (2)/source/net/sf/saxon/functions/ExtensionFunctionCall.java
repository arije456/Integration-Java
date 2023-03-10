package net.sf.saxon.functions;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.EmptyIterator;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SingletonIterator;
import net.sf.saxon.type.ItemType;
import net.sf.saxon.type.AnyItemType;
import net.sf.saxon.type.Type;
import net.sf.saxon.value.*;
import net.sf.saxon.xpath.XPathException;
import net.sf.saxon.xpath.DynamicError;
import net.sf.saxon.expr.*;
import net.sf.saxon.Configuration;
import net.sf.saxon.pattern.AnyNodeTest;

import java.lang.reflect.*;
import java.util.List;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.transform.Source;



/**
* This class acts as a container for an extension function defined to call a method
* in a user-defined class.
*/

public class ExtensionFunctionCall extends FunctionCall {

    private AccessibleObject theMethod;
    private Class theClass;

    /**
     * Default constructor
     */

    public ExtensionFunctionCall() {}

    /**
    * Constructor: creates an ExtensionFunctionCall
     * @param nameCode the name code of the function, for display purposes
     * @param theClass the Java class containing the method to be called
     * @param object the method, field, or constructor of the Java class to be called
    */

    public ExtensionFunctionCall(int nameCode, Class theClass, AccessibleObject object) {
        init(nameCode, theClass, object);
    }

    /**
     * Initialization: creates an ExtensionFunctionCall
     * @param nameCode the name code of the function, for display purposes
     * @param theClass the Java class containing the method to be called
     * @param object the method, field, or constructor of the Java class to be called
    */

    public void init(int nameCode, Class theClass, AccessibleObject object) {
        setFunctionNameCode(nameCode);
        this.theClass = theClass;
        this.theMethod = object;
    }

    /**
    * preEvaluate: this method suppresses compile-time evaluation by doing nothing
    * (because the external function might have side-effects and might use the context)
    */

    public Expression preEvaluate(StaticContext env) {
        return this;
    }


    /**
    * Method called by the expression parser when all arguments have been supplied
    */

    public void checkArguments(StaticContext env) throws XPathException {
    }


    /**
    * Determine which aspects of the context the expression depends on. The result is
    * a bitwise-or'ed value composed from constants such as XPathContext.VARIABLES and
    * XPathContext.CURRENT_NODE
    */

    public int getIntrinsicDependencies() {
        if (theMethod instanceof Method) {
            Class[] theParameterTypes = ((Method)theMethod).getParameterTypes();
            if (theParameterTypes.length > 0 && theParameterTypes[0] == XPathContext.class) {
                return StaticProperty.DEPENDS_ON_CONTEXT_ITEM |
                        StaticProperty.DEPENDS_ON_POSITION |
                        StaticProperty.DEPENDS_ON_LAST;
            }
        }
        return 0;
    }


    /**
    * Evaluate the function. <br>
    * @param context The context in which the function is to be evaluated
    * @return a Value representing the result of the function.
    * @throws net.sf.saxon.xpath.XPathException if the function cannot be evaluated.
    */

    public SequenceIterator iterate(XPathContext context) throws XPathException {
        Value[] argValues = new Value[argument.length];
        for (int i=0; i<argValues.length; i++) {
            argValues[i] = ExpressionTool.lazyEvaluate(argument[i], context);
        }
        return call(argValues, context);
    }

    /**
     * Get the class containing the method being called
     */

    public Class getTargetClass() {
        return theClass;
    }

    /**
     * Get the target method (or field, or constructor) being called
     */

    public AccessibleObject getTargetMethod() {
        return theMethod;
    }


    /**
     * Call an extension function previously identified using the bind() method. A subclass
     * can override this method.
     * @param argValues  The values of the arguments
     * @return  The value returned by the extension function
     */

    public SequenceIterator call(Value[] argValues, XPathContext context) throws XPathException {
        Configuration config = context.getController().getConfiguration();

        // now call it

        Class[] theParameterTypes;

        if (theMethod instanceof Constructor) {
            Constructor constructor = (Constructor) theMethod;
            theParameterTypes = constructor.getParameterTypes();
            Object[] params = new Object[theParameterTypes.length];

            setupParams(argValues, params, theParameterTypes, 0, 0, context);

            try {
                Object result = invokeConstructor(constructor, params);
                //Object result = constructor.newInstance(params);

                return asIterator(result, context);
            } catch (InstantiationException err0) {
                DynamicError e = new DynamicError("Cannot instantiate class", err0);
                e.setXPathContext(context);
                throw e;
            } catch (IllegalAccessException err1) {
                DynamicError e =  new DynamicError("Constructor access is illegal", err1);
                e.setXPathContext(context);
                throw e;
            } catch (IllegalArgumentException err2) {
                DynamicError e =  new DynamicError("Argument is of wrong type", err2);
                e.setXPathContext(context);
                throw e;
            } catch (NullPointerException err2) {
                DynamicError e =  new DynamicError("Object is null");
                e.setXPathContext(context);
                throw e;
            } catch (InvocationTargetException err3) {
                Throwable ex = err3.getTargetException();
                if (ex instanceof XPathException) {
                    throw (XPathException) ex;
                } else {
                    if (context.getController().isTracing() ||
                            context.getController().getConfiguration().isTraceExternalFunctions()) {
                        err3.getTargetException().printStackTrace();
                    }
                    DynamicError e = new DynamicError("Exception in extension function: " +
                            err3.getTargetException().toString(), ex);
                    e.setXPathContext(context);
                    throw e;
                }
            }
        } else if (theMethod instanceof Method) {
            Method method = (Method) theMethod;
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            Object theInstance;
            theParameterTypes = method.getParameterTypes();
            boolean usesContext = theParameterTypes.length > 0 &&
                    (theParameterTypes[0] == XPathContext.class);
            if (isStatic) {
                theInstance = null;
            } else {
                if (argValues.length == 0) {
                    DynamicError e = new DynamicError("Must supply an argument for an instance-level extension function");
                    e.setXPathContext(context);
                    throw e;
                }
                Value arg0 = argValues[0];
                theInstance = arg0.convertToJava(theClass, config, context);
                // this fails if the first argument is not of a suitable class
            }

            Object[] params = new Object[theParameterTypes.length];

            if (usesContext) {
                params[0] = context;
            }

            setupParams(argValues, params, theParameterTypes,
                    (usesContext ? 1 : 0),
                    (isStatic ? 0 : 1),
                    context
            );

            try {
                Object result = invokeMethod(method, theInstance, params);
                //Object result = method.invoke(theInstance, params);
                if (method.getReturnType().toString().equals("void")) {
                    // method returns void:
                    // tried (method.getReturnType()==Void.class) unsuccessfully
                    return EmptyIterator.getInstance();
                }
                return asIterator(result, context);

            } catch (IllegalAccessException err1) {
                throw new DynamicError("Method access is illegal", err1);
            } catch (IllegalArgumentException err2) {
                throw new DynamicError("Argument is of wrong type", err2);
            } catch (NullPointerException err2) {
                throw new DynamicError("Object is null", err2);
            } catch (InvocationTargetException err3) {
                Throwable ex = err3.getTargetException();
                if (ex instanceof XPathException) {
                    throw (XPathException) ex;
                } else {
                    if (context.getController().isTracing() ||
                            context.getController().getConfiguration().isTraceExternalFunctions()) {
                        err3.getTargetException().printStackTrace();
                    }
                    throw new DynamicError("Exception in extension function " +
                            err3.getTargetException().toString(), ex);
                }
            }
        } else if (theMethod instanceof Field) {

            // Start of code added by GS

            Field field = (Field) theMethod;
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            Object theInstance;
            if (isStatic) {
                theInstance = null;
            } else {
                if (argValues.length == 0) {
                    DynamicError e = new DynamicError("Must supply an argument for an instance-level extension function");
                    e.setXPathContext(context);
                    throw e;
                }
                Value arg0 = argValues[0];
                theInstance = arg0.convertToJava(theClass, config, context);
                // this fails if the first argument is not of a suitable class
            }

            try {
                Object result = getField(field, theInstance);
                return asIterator(result, context);

            } catch (IllegalAccessException err1) {
                DynamicError e = new DynamicError("Field access is illegal", err1);
                e.setXPathContext(context);
                throw e;
            } catch (IllegalArgumentException err2) {
                DynamicError e = new DynamicError("Argument is of wrong type", err2);
                e.setXPathContext(context);
                throw e;
            }
        } else {
            throw new AssertionError("property " + theMethod + " is neither constructor, method, nor field");
        }

    }

    /**
     * Convert the extension function result to an XPath value (a sequence) and return a
     * SequenceIterator over that sequence
     * @param result the result returned by the Java extension function
     * @param context the dynamic context
     * @return an iterator over the items in the result
     * @throws net.sf.saxon.xpath.XPathException
     */

    private SequenceIterator asIterator(Object result, XPathContext context) throws XPathException {
        if (result == null) {
            return EmptyIterator.getInstance();
        }
        if (result instanceof SequenceIterator) {
            return (SequenceIterator) result;
        }
        if (result instanceof Value) {
            return ((Value) result).iterate(null);
        }
        if (result instanceof NodeInfo) {
            return SingletonIterator.makeIterator(((NodeInfo) result));
        }
        Value actual = Value.convertJavaObjectToXPath(result, context.getController());
        return actual.iterate(context);
    }

    /**
     * Set up parameters for the Java method call
     * @param argValues the supplied XPath argument values
     * @param params the result of converting the XPath argument values to Java objects
     * @param paramTypes the Java classes defining the types of the arguments in the method signature
     * @param firstParam normally 0, but 1 if the first parameter to the Java method is an XPathContext object
     * @param firstArg normally 0, but 1 if the first argument in the XPath call is the instance object whose method
     * is to be called
     * @param context The dynamic context, giving access to a NamePool and to schema information
     * @throws net.sf.saxon.xpath.XPathException
     */

    private void setupParams(Value[] argValues,
                             Object[] params,
                             Class[] paramTypes,
                             int firstParam,
                             int firstArg,
                             XPathContext context) throws XPathException {
        int j = firstParam;
        for (int i = firstArg; i < argValues.length; i++) {
            params[j] = argValues[i].convertToJava(paramTypes[j], context.getController().getConfiguration(), context);
            j++;
        }
    }

    /**
     * Determine the data type of the expression, if possible. All expressions return
     * sequences, in general; this method determines the type of the items within the
     * sequence, assuming that (a) this is known in advance, and (b) it is the same for
     * all items in the sequence.
     *
     * <p>This method will always return a result, though it may be the best approximation
     * that is available at the time.</p>
     *
     * @return the item type
     */

    public ItemType getItemType() {
        Class resultClass = getReturnClass();
        if (resultClass==null || resultClass==Value.class) {
            return AnyItemType.getInstance();
        } else if (resultClass.toString().equals("void")) {
            return AnyItemType.getInstance();
        } else if (resultClass==String.class || resultClass==StringValue.class) {
            return Type.STRING_TYPE;
        } else if (resultClass==Boolean.class || resultClass==boolean.class || resultClass == BooleanValue.class) {
            return Type.BOOLEAN_TYPE;
        } else if (resultClass==Double.class || resultClass==double.class || resultClass==DoubleValue.class) {
            return Type.DOUBLE_TYPE;
        } else if ( resultClass==Float.class || resultClass==float.class || resultClass==FloatValue.class) {
            return Type.FLOAT_TYPE;
        } else if ( resultClass==Long.class || resultClass==long.class ||
                    resultClass==IntegerValue.class || resultClass==BigIntegerValue.class ||
                    resultClass==Integer.class || resultClass==int.class ||
                    resultClass==Short.class || resultClass==short.class ||
                    resultClass==Byte.class || resultClass==byte.class ) {
            return Type.INTEGER_TYPE;
        } else if (SequenceValue.class.isAssignableFrom(resultClass) ||
                    SequenceIterator.class.isAssignableFrom(resultClass)) {
            return AnyItemType.getInstance();
        } else if ( NodeList.class.isAssignableFrom(resultClass) ||
                    NodeInfo.class.isAssignableFrom(resultClass) ||
                    Node.class.isAssignableFrom(resultClass) ||
                    Source.class.isAssignableFrom(resultClass)) {
            return AnyNodeTest.getInstance();
            // we could be more specific regarding the kind of node
        } else {
            return AnyItemType.getInstance();
        }
    }

    public int computeCardinality() {
        Class resultClass = getReturnClass();
        if (resultClass==null) {
            // we don't know yet
            return StaticProperty.ALLOWS_ZERO_OR_MORE;
        }
        if (SequenceValue.class.isAssignableFrom(resultClass) ||
                    SequenceIterator.class.isAssignableFrom(resultClass) ||
                    NodeList.class.isAssignableFrom(resultClass) ||
                    List.class.isAssignableFrom(resultClass) ||
                    Closure.class.isAssignableFrom(resultClass)||
                    Source.class.isAssignableFrom(resultClass) ||
                    resultClass.isArray()) {
            return StaticProperty.ALLOWS_ZERO_OR_MORE;
        } else if (resultClass.isPrimitive()) {
            if (resultClass.equals(Void.TYPE)) {
                // this always returns an empty sequence, but we'll model it as
                // zero or one
                return StaticProperty.ALLOWS_ZERO_OR_ONE;
            } else {
                // return type = int, boolean, char etc
                return StaticProperty.EXACTLY_ONE;
            }
        } else {
            return StaticProperty.ALLOWS_ZERO_OR_ONE;
        }
    }

    /**
     * Get the Java class of the value returned by the method
     * @return the Java class of the value returned by the method
     */

    private Class getReturnClass() {
        if (theMethod instanceof Method) {
            return ((Method)theMethod).getReturnType();
        } else if (theMethod instanceof Field) {
            return ((Field)theMethod).getType();
        } else if (theMethod instanceof Constructor) {
            return theClass;
        } else {
            // cannot happen
            return null;
        }
    }

    /**
     * Determine whether this method uses the focus. True if the first argument is of type XPathContext.
     */

    public boolean usesFocus() {
        if (theMethod instanceof Method) {
            Class[] theParameterTypes = ((Method)theMethod).getParameterTypes();
            return theParameterTypes.length > 0 && (theParameterTypes[0] == XPathContext.class);
        } else {
            return false;
        }
    }

    /**
     * Invoke a constructor. This method is provided separately so that it can be refined in a subclass.
     * For example, a subclass might perform tracing of calls, or might trap exceptions.
     * @param constructor The constructor to be invoked
     * @param params The parameters to be passed to the constructor
     * @return The object returned by the constructor
     * @throws InstantiationException if the invocation throws an InstantiationException
     * @throws IllegalAccessException if the invocation throws an IllegalAccessException
     * @throws InvocationTargetException if the invocation throws an InvocationTargetException (which happens
     * when the constructor itself throws an exception)
     */

    protected Object invokeConstructor(Constructor constructor, Object[] params)
    throws java.lang.InstantiationException,
           java.lang.IllegalAccessException,
           java.lang.reflect.InvocationTargetException {
        return constructor.newInstance(params);
    }

    /**
     * Invoke a method. This method is provided separately so that it can be refined in a subclass.
     * For example, a subclass might perform tracing of calls, or might trap exceptions.
     * @param method The method to be invoked
     * @param instance The object on which the method is to be invoked. This is set to null if the
     * method is static.
     * @param params The parameters to be passed to the method
     * @return The object returned by the method
     * @throws IllegalAccessException if the invocation throws an IllegalAccessException
     * @throws InvocationTargetException if the invocation throws an InvocationTargetException (which happens
     * when the method itself throws an exception)
     */

    protected Object invokeMethod(Method method, Object instance, Object[] params)
    throws java.lang.IllegalAccessException,
           java.lang.reflect.InvocationTargetException {
        return method.invoke(instance, params);
    }

    /**
     * Access a field. This method is provided separately so that it can be refined in a subclass.
     * For example, a subclass might perform tracing of calls, or might trap exceptions.
     * @param field The field to be retrieved
     * @param instance The object whose field is to be retrieved. This is set to null if the
     * field is static.
     * @return The value of the field
     * @throws IllegalAccessException if the invocation throws an IllegalAccessException
     */

    protected Object getField(Field field, Object instance)
    throws java.lang.IllegalAccessException {
        return field.get(instance);
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
// Contributor(s): Gunther Schadow (changes to allow access to public fields; also wrapping
// of extensions and mapping of null to empty sequence).
//
