package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class RetExpression implements Expression {
    @Override
    public String toString() {
        return "Ret";
    }

    @Override
    public int hashCode() {
        return 43;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RetExpression);
    }
}
