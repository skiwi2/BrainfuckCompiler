package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class PushExpression implements Expression {
    @Override
    public String toString() {
        return "Push";
    }

    @Override
    public int hashCode() {
        return 34;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PushExpression);
    }
}
