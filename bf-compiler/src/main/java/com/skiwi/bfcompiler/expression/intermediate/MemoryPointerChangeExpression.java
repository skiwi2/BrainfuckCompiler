package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class MemoryPointerChangeExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "MemoryPointerChange";
    }

    @Override
    public int hashCode() {
        return 14;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MemoryPointerChangeExpression);
    }
}
