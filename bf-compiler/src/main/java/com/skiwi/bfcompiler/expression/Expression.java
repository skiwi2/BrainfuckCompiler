package com.skiwi.bfcompiler.expression;

/**
 * @author Frank van Heeswijk
 */
public interface Expression {
    default boolean isLogicalLeafExpression() {
        // this method only makes sense for non-target expressions
        throw new UnsupportedOperationException();
    }
}
