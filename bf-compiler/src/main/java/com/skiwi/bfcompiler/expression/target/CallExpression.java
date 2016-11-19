package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class CallExpression implements Expression {
    @Override
    public String toString() {
        return "Call";
    }

    @Override
    public int hashCode() {
        return 36;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CallExpression);
    }
}
