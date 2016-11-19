package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class DataSectionExpression implements Expression {
    @Override
    public String toString() {
        return "DataSection";
    }

    @Override
    public int hashCode() {
        return 26;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DataSectionExpression);
    }
}
