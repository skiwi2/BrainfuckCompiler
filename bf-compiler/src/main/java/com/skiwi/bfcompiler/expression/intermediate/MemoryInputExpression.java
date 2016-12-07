package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemoryInputExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "MemoryInput";
    }

    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryInputExpression);
    }
}
