package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class JmpExpression implements Expression {
    @Override
    public String toString() {
        return "Jmp";
    }

    @Override
    public int hashCode() {
        return 41;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JmpExpression);
    }
}
