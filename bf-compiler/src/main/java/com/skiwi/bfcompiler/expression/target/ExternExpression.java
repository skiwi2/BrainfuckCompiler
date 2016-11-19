package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class ExternExpression implements Expression {
    @Override
    public String toString() {
        return "Extern";
    }

    @Override
    public int hashCode() {
        return 22;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ExternExpression);
    }
}
