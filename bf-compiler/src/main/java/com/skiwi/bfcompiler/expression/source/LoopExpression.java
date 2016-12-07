package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class LoopExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return false;
    }

    @Override
    public String toString() {
        return "Loop";
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LoopExpression);
    }
}
