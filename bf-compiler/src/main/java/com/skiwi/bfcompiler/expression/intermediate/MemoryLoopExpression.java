package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemoryLoopExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return false;
    }

    @Override
    public String toString() {
        return "MemoryLoop";
    }

    @Override
    public int hashCode() {
        return 16;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryLoopExpression);
    }
}
