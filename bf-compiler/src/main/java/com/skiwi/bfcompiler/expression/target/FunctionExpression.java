package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class FunctionExpression implements Expression {
    @Override
    public String toString() {
        return "Function";
    }

    @Override
    public int hashCode() {
        return 23;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof FunctionExpression);
    }
}
