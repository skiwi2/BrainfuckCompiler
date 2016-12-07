package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank van Heeswijk
 */
public class OptimizeStrategyNextResult {
    private final OptimizeStrategyNextState state;
    private final List<ASTNode> newNodes = new ArrayList<>();

    private OptimizeStrategyNextResult(final OptimizeStrategyNextState state, final List<ASTNode> newNodes) {
        this.state = state;
        this.newNodes.addAll(newNodes);
    }

    public OptimizeStrategyNextState getState() {
        return state;
    }

    public List<ASTNode> getNewNodes() {
        return newNodes;
    }

    @Override
    public String toString() {
        return "OptimizeStrategyNextResult(" + state + ", " + newNodes + ")";
    }

    public static OptimizeStrategyNextResult accepted() {
        return new OptimizeStrategyNextResult(OptimizeStrategyNextState.ACCEPTED, new ArrayList<>());
    }

    public static OptimizeStrategyNextResult finished(final List<ASTNode> newNodes) {
        return new OptimizeStrategyNextResult(OptimizeStrategyNextState.FINISHED, newNodes);
    }
}
