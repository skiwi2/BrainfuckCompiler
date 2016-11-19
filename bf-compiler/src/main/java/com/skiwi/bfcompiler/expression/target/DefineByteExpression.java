package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class DefineByteExpression implements Expression {
    @Override
    public String toString() {
        return "DefineByte";
    }

    @Override
    public int hashCode() {
        return 29;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DefineByteExpression);
    }
}
