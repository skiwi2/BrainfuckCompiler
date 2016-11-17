package com.skiwi.bfcompiler.ast;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class AST {
    private final ASTNode root;

    public AST(final ASTNode root) {
        this.root = root;
    }

    public ASTNode getRoot() {
        return root;
    }

    public Stream<String> prettyPrintStream() {
        return root.prettyPrintStream("", false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AST ast = (AST)obj;
        return Objects.equals(root, ast.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}
