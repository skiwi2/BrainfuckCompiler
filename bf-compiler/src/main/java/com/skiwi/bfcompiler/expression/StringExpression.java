package com.skiwi.bfcompiler.expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class StringExpression implements Expression {
    private String string;

    public StringExpression(final String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public boolean isLogicalLeafExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "String(" + string + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StringExpression that = (StringExpression)obj;
        return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }
}
