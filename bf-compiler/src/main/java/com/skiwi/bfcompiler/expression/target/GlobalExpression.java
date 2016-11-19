package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class GlobalExpression implements Expression {
    @Override
    public String toString() {
        return "Global";
    }

    @Override
    public int hashCode() {
        return 32;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GlobalExpression);
    }
}
