package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryPointerChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;

import java.util.Arrays;

/**
 * @author Frank van Heeswijk
 */
public class MemoryPointerOptimizeStrategy implements OptimizeStrategy {
    private int change = 0;

    @Override
    public OptimizeStrategyResult processNode(final ASTNode node, final boolean isLastNode) {
        Expression expression = node.getExpression();

        if (!(expression instanceof MemoryPointerChangeExpression)) {
            return OptimizeStrategyResult.notAccepted();
        }

        change += node.getChildExpression(0, IntegerExpression.class).getInteger();
        if (isLastNode) {
            return OptimizeStrategyResult.finished(Arrays.asList(createNewNode()));
        }
        else {
            return OptimizeStrategyResult.accepted();
        }
    }

    @Override
    public OptimizeStrategyNextResult processNextNode(ASTNode node, final boolean isLastNode) {
        Expression expression = node.getExpression();

        if (isLastNode || !(expression instanceof MemoryPointerChangeExpression)) {
            return OptimizeStrategyNextResult.finished(Arrays.asList(createNewNode()));
        }
        else {
            change += node.getChildExpression(0, IntegerExpression.class).getInteger();
            return OptimizeStrategyNextResult.accepted();
        }
    }

    @Override
    public void resetState() {
        change = 0;
    }

    @Override
    public String toString() {
        return "MemoryPointerOptimizeStrategy(" + change + ")";
    }

    private ASTNode createNewNode() {
        return ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(change)));
    }
}
