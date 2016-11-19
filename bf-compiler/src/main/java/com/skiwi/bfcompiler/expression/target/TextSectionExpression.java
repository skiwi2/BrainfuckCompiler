package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

/**
 * @author Frank van Heeswijk
 */
public class TextSectionExpression implements Expression {
    @Override
    public String toString() {
        return "TextSection";
    }

    @Override
    public int hashCode() {
        return 28;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TextSectionExpression);
    }
}

