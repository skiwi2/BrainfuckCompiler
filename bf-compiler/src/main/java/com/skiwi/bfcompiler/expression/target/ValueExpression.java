package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class ValueExpression implements Expression {
    @Override
    public String toString() {
        return "Value";
    }

    @Override
    public int hashCode() {
        return 25;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ValueExpression);
    }
}
