package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemorySetValueExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "MemorySetValue";
    }

    @Override
    public int hashCode() {
        return 202;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemorySetValueExpression);
    }
}
