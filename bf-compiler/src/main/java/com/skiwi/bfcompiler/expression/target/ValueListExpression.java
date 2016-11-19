package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class ValueListExpression implements Expression {
    @Override
    public String toString() {
        return "ValueList";
    }

    @Override
    public int hashCode() {
        return 30;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ValueListExpression);
    }
}
