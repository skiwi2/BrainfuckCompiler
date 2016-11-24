package com.skiwi.bfcompiler.ast;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class ASTNode {
    private final Expression expression;

    private ASTNode parent;

    private final List<ASTNode> children = new ArrayList<>();

    public ASTNode(final Expression expression) {
        this.expression = expression;
    }

    public static ASTNode newWithChild(final Expression expression, final ASTNode node) {
        return newWithChildren(expression, Arrays.asList(node));
    }

    public static ASTNode newWithChildren(final Expression expression, final List<ASTNode> nodes) {
        ASTNode newNode = new ASTNode(expression);
        nodes.forEach(newNode::addChild);
        return newNode;
    }

    public static ASTNode newWithMappedChildren(final Expression expression, final List<ASTNode> nodes, final UnaryOperator<ASTNode> mapper) {
        ASTNode newNode = new ASTNode(expression);
        nodes.stream().map(mapper).forEach(newNode::addChild);
        return newNode;
    }

    public Expression getExpression() {
        return expression;
    }

    public <T extends Expression> T getExpression(final Class<T> clazz) {
        if (clazz.isInstance(expression)) {
            return clazz.cast(expression);
        }
        throw new IllegalStateException("Expression " + expression + " cannot be cast to class " + clazz);
    }

    public void setParent(final ASTNode parent) {
        this.parent = parent;
    }

    public ASTNode getParent() {
        return parent;
    }

    public void addChild(ASTNode node) {
        children.add(node);
        node.parent = this;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public ASTNode getChild(final int index, final Class<? extends Expression> clazz) {
        ASTNode node = children.get(index);
        Expression childExpression = node.getExpression();
        if (!clazz.isInstance(childExpression)) {
            throw new IllegalStateException("Expected child " + index + " to be of class " + clazz + ", but it is " + childExpression);
        }
        return node;
    }

    public <T extends Expression> T getChildExpression(final int index, final Class<T> clazz) {
        ASTNode node = children.get(index);
        return node.getExpression(clazz);
    }

    public void detach() {
        parent = null;
    }

    protected Stream<String> prettyPrintStream(String prefix, boolean tail) {
        return Stream.concat(
            Stream.of(prefix + (tail ? "└── " : "├── ") + expression),
            IntStream.range(0, children.size())
                .boxed()
                .flatMap(i -> children.get(i).prettyPrintStream(prefix + (tail ? "    " : "│   "), (i == children.size() - 1)))
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ASTNode node = (ASTNode)obj;
        return Objects.equals(expression, node.expression) &&
            Objects.equals(children, node.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, children);
    }
}
