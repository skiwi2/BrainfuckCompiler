package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class MemoryValueChangeExpression implements Expression {
    @Override
    public String toString() {
        return "MemoryValueChange";
    }

    @Override
    public int hashCode() {
        return 15;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryValueChangeExpression);
    }
}
