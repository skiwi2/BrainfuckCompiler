package com.skiwi.bfcompiler.expression;

/**
 * @author Frank van Heeswijk
 */
public class RootExpression implements Expression {
    @Override
    public boolean isLogicalLeafExpression() {
        return false;
    }

    @Override
    public String toString() {
        return "Root";
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RootExpression);
    }
}
