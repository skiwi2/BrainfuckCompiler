package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class JzExpression implements Expression {
    @Override
    public String toString() {
        return "Jz";
    }

    @Override
    public int hashCode() {
        return 40;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JzExpression);
    }
}
