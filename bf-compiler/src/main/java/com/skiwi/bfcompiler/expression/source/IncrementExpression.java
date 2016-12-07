package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class IncrementExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "Increment";
    }

    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IncrementExpression);
    }
}
