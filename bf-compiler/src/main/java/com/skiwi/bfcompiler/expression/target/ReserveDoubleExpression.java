package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class ReserveDoubleExpression implements Expression {
    @Override
    public String toString() {
        return "ReserveDouble";
    }

    @Override
    public int hashCode() {
        return 30;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ReserveDoubleExpression);
    }
}

