package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class XorExpression implements Expression {
    @Override
    public String toString() {
        return "Xor";
    }

    @Override
    public int hashCode() {
        return 301;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof XorExpression);
    }
}
