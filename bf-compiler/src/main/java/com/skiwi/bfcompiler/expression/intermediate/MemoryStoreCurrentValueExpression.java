package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemoryStoreCurrentValueExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "MemoryStoreCurrentValue";
    }

    @Override
    public int hashCode() {
        return 201;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryStoreCurrentValueExpression);
    }
}
