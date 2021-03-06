package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class AddExpression implements Expression {
    @Override
    public String toString() {
        return "Add";
    }

    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AddExpression);
    }
}
