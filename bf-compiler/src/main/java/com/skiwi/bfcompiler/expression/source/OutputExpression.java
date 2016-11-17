package com.skiwi.bfcompiler.expression.source;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class OutputExpression implements Expression {
    @Override
    public String toString() {
        return "Output";
    }

    @Override
    public int hashCode() {
        return 6;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof OutputExpression);
    }
}
