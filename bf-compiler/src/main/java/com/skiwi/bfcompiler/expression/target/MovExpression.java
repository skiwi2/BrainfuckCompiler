package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MovExpression implements Expression {
    @Override
    public String toString() {
        return "Mov";
    }

    @Override
    public int hashCode() {
        return 33;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MovExpression);
    }
}
