package com.skiwi.bfcompiler.writer;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.StringExpression;
import com.skiwi.bfcompiler.expression.target.AddExpression;
import com.skiwi.bfcompiler.expression.target.BssSectionExpression;
import com.skiwi.bfcompiler.expression.target.CallExpression;
import com.skiwi.bfcompiler.expression.target.DataSectionExpression;
import com.skiwi.bfcompiler.expression.target.DefineByteExpression;
import com.skiwi.bfcompiler.expression.target.DefineLabelExpression;
import com.skiwi.bfcompiler.expression.target.DwordExpression;
import com.skiwi.bfcompiler.expression.target.ExternExpression;
import com.skiwi.bfcompiler.expression.target.FunctionExpression;
import com.skiwi.bfcompiler.expression.target.GlobalExpression;
import com.skiwi.bfcompiler.expression.target.IdentifierExpression;
import com.skiwi.bfcompiler.expression.target.JmpExpression;
import com.skiwi.bfcompiler.expression.target.JmpShortExpression;
import com.skiwi.bfcompiler.expression.target.JnzExpression;
import com.skiwi.bfcompiler.expression.target.JzExpression;
import com.skiwi.bfcompiler.expression.target.LabelExpression;
import com.skiwi.bfcompiler.expression.target.MemoryAddressExpression;
import com.skiwi.bfcompiler.expression.target.MovExpression;
import com.skiwi.bfcompiler.expression.target.MulExpression;
import com.skiwi.bfcompiler.expression.target.OperandExpression;
import com.skiwi.bfcompiler.expression.target.PushExpression;
import com.skiwi.bfcompiler.expression.target.RegisterExpression;
import com.skiwi.bfcompiler.expression.target.ReserveDoubleExpression;
import com.skiwi.bfcompiler.expression.target.RetExpression;
import com.skiwi.bfcompiler.expression.target.TestExpression;
import com.skiwi.bfcompiler.expression.target.TextSectionExpression;
import com.skiwi.bfcompiler.expression.target.ValueExpression;
import com.skiwi.bfcompiler.expression.target.ValueListExpression;
import com.skiwi.bfcompiler.expression.target.XorExpression;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class TargetCodeWriter {
    public Stream<String> write(final AST targetAst) {
        return targetAst.getRoot().getChildren().stream()
            .flatMap(this::resolveNode);
    }

    private Stream<String> resolveNode(final ASTNode node) {
        Expression expression = node.getExpression();
        if (expression instanceof ExternExpression) {
            String functions = node.getChildren().stream()
                .flatMap(n -> resolveNode(n, FunctionExpression.class))
                .collect(Collectors.joining(", "));
            return Stream.of("extern " + functions);
        }
        if (expression instanceof FunctionExpression) {
            return Stream.of(resolveFunction(node));
        }
        if (expression instanceof DataSectionExpression) {
            return Stream.concat(
                Stream.of("section .data"),
                resolveChildren(node)
            );
        }
        if (expression instanceof DefineByteExpression) {
            String identifier = resolveIdentifier(node.getChild(0, IdentifierExpression.class));
            String value = resolveValue(node.getChild(1, ValueExpression.class));
            return Stream.of(identifier + " db " + value);
        }
        if (expression instanceof BssSectionExpression) {
            return Stream.concat(
                Stream.of("section .bss"),
                resolveChildren(node)
            );
        }
        if (expression instanceof ReserveDoubleExpression) {
            String identifier = resolveIdentifier(node.getChild(0, IdentifierExpression.class));
            String value = resolveValue(node.getChild(1, ValueExpression.class));
            return Stream.of(identifier + " resd " + value);
        }
        if (expression instanceof TextSectionExpression) {
            return Stream.concat(
                Stream.of("section .text"),
                resolveChildren(node)
            );
        }
        if (expression instanceof GlobalExpression) {
            String label = resolveLabel(node.getChild(0, LabelExpression.class));
            return Stream.of("global " + label);
        }
        if (expression instanceof DefineLabelExpression) {
            String label = resolveLabel(node.getChild(0, LabelExpression.class));
            return Stream.of(label + ":");
        }
        if (expression instanceof MovExpression) {
            String operand1 = resolveOperand(node.getChild(0, OperandExpression.class));
            String operand2 = resolveOperand(node.getChild(1, OperandExpression.class));
            return Stream.of("mov " + operand1 + ", " + operand2);
        }
        if (expression instanceof PushExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("push " + operand);
        }
        if (expression instanceof CallExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("call " + operand);
        }
        if (expression instanceof AddExpression) {
            String operand1 = resolveOperand(node.getChild(0, OperandExpression.class));
            String operand2 = resolveOperand(node.getChild(1, OperandExpression.class));
            return Stream.of("add " + operand1 + ", " + operand2);
        }
        if (expression instanceof TestExpression) {
            String operand1 = resolveOperand(node.getChild(0, OperandExpression.class));
            String operand2 = resolveOperand(node.getChild(1, OperandExpression.class));
            return Stream.of("test " + operand1 + ", " + operand2);
        }
        if (expression instanceof JzExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("jz " + operand);
        }
        if (expression instanceof JnzExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("jnz " + operand);
        }
        if (expression instanceof JmpExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("jmp " + operand);
        }
        if (expression instanceof JmpShortExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("jmp short " + operand);
        }
        if (expression instanceof RetExpression) {
            return Stream.of("ret");
        }
        if (expression instanceof XorExpression) {
            String operand1 = resolveOperand(node.getChild(0, OperandExpression.class));
            String operand2 = resolveOperand(node.getChild(1, OperandExpression.class));
            return Stream.of("xor " + operand1 + ", " + operand2);
        }
        if (expression instanceof MulExpression) {
            String operand = resolveOperand(node.getChild(0, OperandExpression.class));
            return Stream.of("mul " + operand);
        }
        throw new IllegalStateException("Unsupported expression: " + expression);
    }

    private Stream<String> resolveNode(final ASTNode node, final Class<? extends Expression> expectedExpression) {
        Expression expression = node.getExpression();
        if (!expectedExpression.isInstance(expression)) {
            throw new IllegalStateException("Cannot resolve node " + node + ": Expected " + expectedExpression + ", found " + expression);
        }
        return resolveNode(node);
    }

    private Stream<String> resolveChildren(final ASTNode node) {
        return node.getChildren().stream().flatMap(this::resolveNode);
    }

    private String resolveIdentifier(final ASTNode node) {
        return node.getChildExpression(0, StringExpression.class).getString();
    }

    private String resolveValue(final ASTNode node) {
        Expression expression = node.getExpression();
        if (expression instanceof ValueExpression) {
            return resolveValue(node.getChild(0, Expression.class));
        }
        if (expression instanceof ValueListExpression) {
            return node.getChildren().stream().map(this::resolveValue).collect(Collectors.joining(", "));
        }
        if (expression instanceof StringExpression) {
            return "\"" + resolveString(node) + "\"";
        }
        if (expression instanceof IntegerExpression) {
            return resolveInteger(node);
        }
        throw new IllegalStateException("Unsupported expression while resolving value: " + expression);
    }

    private String resolveString(final ASTNode node) {
        return node.getExpression(StringExpression.class).getString();
    }

    private String resolveInteger(final ASTNode node) {
        return String.valueOf(node.getExpression(IntegerExpression.class).getInteger());
    }

    private String resolveLabel(final ASTNode node) {
        return node.getChildExpression(0, StringExpression.class).getString();
    }

    private String resolveOperand(final ASTNode node) {
        Expression expression = node.getExpression();
        if (expression instanceof OperandExpression) {
            return resolveOperand(node.getChild(0, Expression.class));
        }
        if (expression instanceof RegisterExpression) {
            return resolveRegister(node);
        }
        if (expression instanceof DwordExpression) {
            return "dword " + resolveOperand(node.getChild(0, Expression.class));
        }
        if (expression instanceof IntegerExpression) {
            return resolveInteger(node);
        }
        if (expression instanceof FunctionExpression) {
            return resolveFunction(node);
        }
        if (expression instanceof LabelExpression) {
            return resolveLabel(node);
        }
        if (expression instanceof MemoryAddressExpression) {
            return "[" + resolveOperand(node.getChild(0, Expression.class)) + "]";
        }
        if (expression instanceof IdentifierExpression) {
            return resolveIdentifier(node);
        }
        throw new IllegalStateException("Unsupported expression while resolving operand: " + expression);
    }

    private String resolveRegister(final ASTNode node) {
        return node.getExpression(RegisterExpression.class).getRegister().name().toLowerCase(Locale.ENGLISH);
    }

    private String resolveFunction(final ASTNode node) {
        return node.getChildExpression(0, StringExpression.class).getString();
    }
}
