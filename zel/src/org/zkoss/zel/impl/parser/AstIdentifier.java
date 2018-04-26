/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* Generated By:JJTree: Do not edit this line. AstIdentifier.java */

package org.zkoss.zel.impl.parser;

import org.zkoss.zel.ELClass;
import org.zkoss.zel.ELException;
import org.zkoss.zel.MethodExpression;
import org.zkoss.zel.MethodInfo;
import org.zkoss.zel.MethodNotFoundException;
import org.zkoss.zel.PropertyNotFoundException;
import org.zkoss.zel.ValueExpression;
import org.zkoss.zel.ValueReference;
import org.zkoss.zel.VariableMapper;
import org.zkoss.zel.impl.lang.EvaluationContext;
import org.zkoss.zel.impl.util.MessageFactory;
import org.zkoss.zel.impl.util.Validation;


/**
 * @author Jacob Hookom [jacob@hookom.net]
 */
public final class AstIdentifier extends SimpleNode {
    public AstIdentifier(int id) {
        super(id);
    }

    
    public Class<?> getType(EvaluationContext ctx) throws ELException {
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null) {
            ValueExpression expr = varMapper.resolveVariable(this.image);
            if (expr != null) {
                return expr.getType(ctx.getELContext());
            }
        }
        ctx.setPropertyResolved(false);
        ctx.putContext(AstIdentifier.class, Integer.valueOf(jjtGetNumSiblings())); //20110909, henrichen: might be one variable series, see AstValue
        ctx.putContext(Node.class, this); //20110909, henrichen: might be one variable series, see AstValue
        Class<?> result = ctx.getELResolver().getType(ctx, null, this.image);
        if (!ctx.isPropertyResolved()) {
            throw new PropertyNotFoundException(MessageFactory.get(
                    "error.resolver.unhandled.null", this.image));
        }
        return result;
    }

    
    public Object getValue(EvaluationContext ctx) throws ELException {
        // Lambda parameters
        if (ctx.isLambdaArgument(this.image)) {
            return ctx.getLambdaArgument(this.image);
        }

        // Variable mapper
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null) {
            ValueExpression expr = varMapper.resolveVariable(this.image);
            if (expr != null) {
                return expr.getValue(ctx.getELContext());
            }
        }

        // Import
        Object result = ctx.getImportHandler().resolveClass(this.image);
        if (result != null) {
            return new ELClass((Class<?>) result);
        }
        result = ctx.getImportHandler().resolveStatic(this.image);
        if (result != null) {
            try {
                return ((Class<?>) result).getField(this.image).get(null);
            } catch (IllegalArgumentException e) {
                throw new ELException(e);
            } catch (IllegalAccessException e) {
                throw new ELException(e);
            } catch (NoSuchFieldException e) {
                throw new ELException(e);
            } catch (SecurityException e) {
                throw new ELException(e);
            }
        }

        // EL Resolvers
        ctx.setPropertyResolved(false);
        ctx.putContext(AstIdentifier.class, Integer.valueOf(jjtGetNumSiblings())); //20110909, henrichen: might be one variable series, see AstValue
        ctx.putContext(Node.class, this); //20110909, henrichen: might be one variable series, see AstValue
        result = ctx.getELResolver().getValue(ctx, null, this.image);
        if (ctx.isPropertyResolved()) {
            if (result instanceof Class)
                return new ELClass((Class<?>) result);
            return result;
        }

        //user can set property as resolved to hide null type exception, default is true
        String hideException = System.getProperty("org.zkoss.zel.hideNullTypeException");
        if (hideException == null || "true".equals(hideException.toLowerCase())) {
        	ctx.setPropertyResolved(true);
        }
        
        //in order to support static method, we can't set property resolved 
        //inside XelELResolver.resolve(), and shouldn't throw exception here
        return null;
    }

    
    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null) {
            ValueExpression expr = varMapper.resolveVariable(this.image);
            if (expr != null) {
                return expr.isReadOnly(ctx.getELContext());
            }
        }
        ctx.setPropertyResolved(false);
        boolean result = ctx.getELResolver().isReadOnly(ctx, null, this.image);
        if (!ctx.isPropertyResolved()) {
            throw new PropertyNotFoundException(MessageFactory.get(
                    "error.resolver.unhandled.null", this.image));
        }
        return result;
    }

    
    public void setValue(EvaluationContext ctx, Object value)
            throws ELException {
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null) {
            ValueExpression expr = varMapper.resolveVariable(this.image);
            if (expr != null) {
                expr.setValue(ctx.getELContext(), value);
                return;
            }
        }
        ctx.setPropertyResolved(false);
        ctx.putContext(AstIdentifier.class, Integer.valueOf(jjtGetNumSiblings())); //20110909, henrichen: might be one variable series, see AstValue
        ctx.putContext(Node.class, this); //20110909, henrichen: might be one variable series, see AstValue
        ctx.getELResolver().setValue(ctx, null, this.image, value);
        if (!ctx.isPropertyResolved()) {
            throw new PropertyNotFoundException(MessageFactory.get(
                    "error.resolver.unhandled.null", this.image));
        }
    }

    
    public Object invoke(EvaluationContext ctx, Class<?>[] paramTypes,
            Object[] paramValues) throws ELException {
        return this.getMethodExpression(ctx).invoke(ctx.getELContext(), paramValues);
    }


    
    public MethodInfo getMethodInfo(EvaluationContext ctx,
            Class<?>[] paramTypes) throws ELException {
        return this.getMethodExpression(ctx).getMethodInfo(ctx.getELContext());
    }

    
    public void setImage(String image) {
        if (!Validation.isIdentifier(image)) {
            throw new ELException(MessageFactory.get("error.identifier.notjava",
                    image));
        }
        this.image = image;
    }


    
    public ValueReference getValueReference(EvaluationContext ctx) {
        VariableMapper varMapper = ctx.getVariableMapper();

        if (varMapper == null) {
            return null;
        }

        ValueExpression expr = varMapper.resolveVariable(this.image);

        if (expr == null) {
            return null;
        }

        return expr.getValueReference(ctx);
    }


    private final MethodExpression getMethodExpression(EvaluationContext ctx)
            throws ELException {
        Object obj = null;

        // case A: ValueExpression exists, getValue which must
        // be a MethodExpression
        VariableMapper varMapper = ctx.getVariableMapper();
        ValueExpression ve = null;
        if (varMapper != null) {
            ve = varMapper.resolveVariable(this.image);
            if (ve != null) {
                obj = ve.getValue(ctx);
            }
        }

        // case B: evaluate the identity against the ELResolver, again, must be
        // a MethodExpression to be able to invoke
        if (ve == null) {
            ctx.setPropertyResolved(false);
            obj = ctx.getELResolver().getValue(ctx, null, this.image);
        }

        // finally provide helpful hints
        if (obj instanceof MethodExpression) {
            return (MethodExpression) obj;
        } else if (obj == null) {
            throw new MethodNotFoundException("Identity '" + this.image
                    + "' was null and was unable to invoke");
        } else {
            throw new ELException(
                    "Identity '"
                            + this.image
                            + "' does not reference a MethodExpression instance, returned type: "
                            + obj.getClass().getName());
        }
    }
}
