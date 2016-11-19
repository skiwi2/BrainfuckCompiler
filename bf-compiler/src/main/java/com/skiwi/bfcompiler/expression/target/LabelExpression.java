package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class LabelExpression implements Expression {
    @Override
    public String toString() {
        return "Label";
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LabelExpression);
    }
}
