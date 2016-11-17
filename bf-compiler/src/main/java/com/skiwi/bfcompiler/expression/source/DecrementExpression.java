package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class DecrementExpression implements Expression {
    @Override
    public String toString() {
        return "Decrement";
    }

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DecrementExpression);
    }
}
