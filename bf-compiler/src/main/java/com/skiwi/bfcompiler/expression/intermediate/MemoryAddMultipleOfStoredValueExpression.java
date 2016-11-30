package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class MemoryAddMultipleOfStoredValueExpression implements Expression {
    @Override
    public String toString() {
        return "MemoryAddMultipleOfStoredValue";
    }

    @Override
    public int hashCode() {
        return 203;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryAddMultipleOfStoredValueExpression);
    }
}
