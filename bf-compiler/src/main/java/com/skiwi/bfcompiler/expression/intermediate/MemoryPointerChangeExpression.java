package com.skiwi.bfcompiler.expression.intermediate;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class MemoryPointerChangeExpression implements Expression {
    private final int amount;

    public MemoryPointerChangeExpression(final int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "MemoryPointerChange(" + amount + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryPointerChangeExpression that = (MemoryPointerChangeExpression) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
