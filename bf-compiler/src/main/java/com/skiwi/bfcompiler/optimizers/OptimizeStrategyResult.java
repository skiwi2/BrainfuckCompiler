package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank van Heeswijk
 */
public class OptimizeStrategyResult {
    private final OptimizeStrategyState state;
    private final List<ASTNode> newNodes = new ArrayList<>();

    private OptimizeStrategyResult(final OptimizeStrategyState state, final List<ASTNode> newNodes) {
        this.state = state;
        this.newNodes.addAll(newNodes);
    }

    public OptimizeStrategyState getState() {
        return state;
    }

    public List<ASTNode> getNewNodes() {
        return newNodes;
    }

    @Override
    public String toString() {
        return "OptimizeStrategyResult(" + state + ", " + newNodes + ")";
    }

    public static OptimizeStrategyResult notAccepted() {
        return new OptimizeStrategyResult(OptimizeStrategyState.NOT_ACCEPTED, new ArrayList<>());
    }

    public static OptimizeStrategyResult accepted() {
        return new OptimizeStrategyResult(OptimizeStrategyState.ACCEPTED, new ArrayList<>());
    }

    public static OptimizeStrategyResult finished(final List<ASTNode> newNodes) {
        return new OptimizeStrategyResult(OptimizeStrategyState.FINISHED, newNodes);
    }
}
