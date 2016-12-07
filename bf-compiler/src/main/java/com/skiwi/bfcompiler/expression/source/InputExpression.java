package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class InputExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "Input";
    }

    @Override
    public int hashCode() {
        return 4;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof InputExpression);
    }
}
