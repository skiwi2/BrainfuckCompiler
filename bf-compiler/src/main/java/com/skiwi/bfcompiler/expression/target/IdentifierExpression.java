package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class IdentifierExpression implements Expression {
    @Override
    public String toString() {
        return "Identifier";
    }

    @Override
    public int hashCode() {
        return 24;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IdentifierExpression);
    }
}
