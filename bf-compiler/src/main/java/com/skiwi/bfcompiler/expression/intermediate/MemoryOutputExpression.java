package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemoryOutputExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "MemoryOutput";
    }

    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryOutputExpression);
    }
}
