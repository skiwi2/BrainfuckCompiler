package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;

import java.util.Arrays;

/**
 * @author Frank van Heeswijk
 */
public class MemoryValueOptimizeStrategy implements OptimizeStrategy {
    private int valueChange = 0;

    @Override
    public OptimizeStrategyResult processNode(final ASTNode node, final boolean isLastNode) {
        Expression expression = node.getExpression();

        if (!(expression instanceof MemoryValueChangeExpression)) {
            return OptimizeStrategyResult.notAccepted();
        }

        valueChange += node.getChildExpression(0, IntegerExpression.class).getInteger();
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

        if (isLastNode || !(expression instanceof MemoryValueChangeExpression)) {
            return OptimizeStrategyNextResult.finished(Arrays.asList(createNewNode()));
        }
        else {
            valueChange += node.getChildExpression(0, IntegerExpression.class).getInteger();
            return OptimizeStrategyNextResult.accepted();
        }
    }

    @Override
    public void resetState() {
        valueChange = 0;
    }

    @Override
    public String toString() {
        return "MemoryValueOptimizeStrategy(" + valueChange + ")";
    }

    private ASTNode createNewNode() {
        return ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(valueChange)));
    }
}
