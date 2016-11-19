package com.skiwi.bfcompiler.expression.target;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.Objects;

/**
 * @author Frank van Heeswijk
 */
public class RegisterExpression implements Expression {
    private final Register register;

    public RegisterExpression(final Register register) {
        this.register = register;
    }

    public Register getRegister() {
        return register;
    }

    @Override
    public String toString() {
        return "Register(" + register + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RegisterExpression that = (RegisterExpression)obj;
        return register == that.register;
    }

    @Override
    public int hashCode() {
        return Objects.hash(register);
    }
}
