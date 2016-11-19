package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class DwordExpression implements Expression {
    @Override
    public String toString() {
        return "Dword";
    }

    @Override
    public int hashCode() {
        return 35;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DwordExpression);
    }
}
