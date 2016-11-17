package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class PointerRightExpression implements Expression {
    @Override
    public String toString() {
        return "PointerRight";
    }

    @Override
    public int hashCode() {
        return 8;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PointerRightExpression);
    }
}
