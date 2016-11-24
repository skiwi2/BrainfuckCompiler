package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryPointerChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;

import java.util.Collections;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * @author Frank van Heeswijk
 */
public class IntermediateCodeOptimizer {
    public void optimize(final AST intermediateAST) {
        optimizeNode(intermediateAST.getRoot());
    }

    private void optimizeNode(final ASTNode node) {
        if (node.getChildren().isEmpty()) {
            return;
        }

        node.getChildren().forEach(this::optimizeNode);

        // Find consecutive MemoryPointerChangeExpression and MemoryValueChangeExpression nodes
        ListIterator<ASTNode> listIterator = node.getChildren().listIterator();
        while (listIterator.hasNext()) {
            ASTNode sentinel = listIterator.next();
            int consecutiveNodes = 1;
            int changeAmount = (sentinel.getExpression() instanceof MemoryPointerChangeExpression || sentinel.getExpression() instanceof MemoryValueChangeExpression)
                ? sentinel.getChildExpression(0, IntegerExpression.class).getInteger()
                : 0;

            while (listIterator.hasNext()) {
                ASTNode next = listIterator.next();
                if (!(next.getExpression() instanceof MemoryPointerChangeExpression && sentinel.getExpression() instanceof MemoryPointerChangeExpression)
                    && !(next.getExpression() instanceof MemoryValueChangeExpression && sentinel.getExpression() instanceof MemoryValueChangeExpression)) {
                    listIterator.previous();
                    break;
                }

                consecutiveNodes++;
                changeAmount += next.getChildExpression(0, IntegerExpression.class).getInteger();
                next.detach();
                listIterator.remove();
            }

            if (consecutiveNodes >= 2) {
                listIterator.previous();
                Expression newExpression = (sentinel.getExpression() instanceof MemoryPointerChangeExpression)
                    ? new MemoryPointerChangeExpression() : new MemoryValueChangeExpression();
                ASTNode newNode = ASTNode.newWithChild(newExpression, new ASTNode(new IntegerExpression(changeAmount)));
                newNode.setParent(node);
                listIterator.set(newNode);
            }
        }
    }
}
