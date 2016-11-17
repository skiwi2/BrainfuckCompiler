package com.skiwi.bfcompiler.ast;

import com.skiwi.bfcompiler.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
