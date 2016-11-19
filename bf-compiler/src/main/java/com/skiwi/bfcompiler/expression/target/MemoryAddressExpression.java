package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemoryAddressExpression implements Expression {
    @Override
    public String toString() {
        return "MemoryAddress";
    }

    @Override
    public int hashCode() {
        return 39;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryAddressExpression);
    }
}
