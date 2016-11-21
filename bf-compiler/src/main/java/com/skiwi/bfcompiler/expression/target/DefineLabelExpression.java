package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class DefineLabelExpression implements Expression {
    @Override
    public String toString() {
        return "DefineLabel";
    }

    @Override
    public int hashCode() {
        return 50;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DefineLabelExpression);
    }
}
