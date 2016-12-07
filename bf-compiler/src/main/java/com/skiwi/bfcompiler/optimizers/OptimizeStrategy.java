package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.ASTNode;

import java.util.ListIterator;

/**
 * @author Frank van Heeswijk
 */
public interface OptimizeStrategy {
    OptimizeStrategyResult processNode(final ASTNode node, final boolean isLastNode);

    OptimizeStrategyNextResult processNextNode(final ASTNode node, final boolean isLastNode);

    void resetState();
}
