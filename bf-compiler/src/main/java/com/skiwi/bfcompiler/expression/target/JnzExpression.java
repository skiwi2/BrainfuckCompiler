package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class JnzExpression implements Expression {
    @Override
    public String toString() {
        return "Jnz";
    }

    @Override
    public int hashCode() {
        return 44;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof JnzExpression);
    }
}
