package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class ByteExpression implements Expression {
    @Override
    public String toString() {
        return "Byte";
    }

    @Override
    public int hashCode() {
        return 401;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ByteExpression);
    }
}