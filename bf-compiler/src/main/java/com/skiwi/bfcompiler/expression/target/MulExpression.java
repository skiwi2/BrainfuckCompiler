package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MulExpression implements Expression {
    @Override
    public String toString() {
        return "Mul";
    }

    @Override
    public int hashCode() {
        return 302;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MulExpression);
    }
}