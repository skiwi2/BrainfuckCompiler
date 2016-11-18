package com.skiwi.bfcompiler.expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class IntegerExpression implements Expression {
    private final int integer;

    public IntegerExpression(final int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }

    @Override
    public String toString() {
        return "Integer(" + integer + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IntegerExpression that = (IntegerExpression)obj;
        return integer == that.integer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(integer);
    }
}
