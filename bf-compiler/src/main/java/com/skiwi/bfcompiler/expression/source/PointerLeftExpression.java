package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class PointerLeftExpression implements Expression {
    @Override
    public String toString() {
        return "PointerLeft";
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PointerLeftExpression);
    }
}
