package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class TestExpression implements Expression {
    @Override
    public String toString() {
        return "Test";
    }

    @Override
    public int hashCode() {
        return 38;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TestExpression);
    }
}
