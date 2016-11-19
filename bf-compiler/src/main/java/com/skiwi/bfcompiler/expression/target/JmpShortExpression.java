package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class JmpShortExpression implements Expression {
    @Override
    public String toString() {
        return "JmpShort";
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JmpShortExpression);
    }
}
