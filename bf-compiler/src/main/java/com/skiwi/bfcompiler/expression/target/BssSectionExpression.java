package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class BssSectionExpression implements Expression {
    @Override
    public String toString() {
        return "BssSection";
    }

    @Override
    public int hashCode() {
        return 27;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof BssSectionExpression);
    }
}
