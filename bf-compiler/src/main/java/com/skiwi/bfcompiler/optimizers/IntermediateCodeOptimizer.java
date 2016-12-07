package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Frank van Heeswijk
 */
public class IntermediateCodeOptimizer {
    private final List<OptimizeStrategy> optimizeStrategies = new ArrayList<>();

    public IntermediateCodeOptimizer(final List<OptimizeStrategy> optimizeStrategies) {
        this.optimizeStrategies.addAll(optimizeStrategies);
    }

    public void optimize(final AST intermediateAST) {
        optimizeNode(intermediateAST.getRoot());
    }

    private void optimizeNode(final ASTNode parentNode) {
        if (parentNode.getChildren().isEmpty()) {
            // TODO change this check to a logical has no children, as a node with IntegerExpression should not count as a child
            return;
        }

        // branch into children first
        parentNode.getChildren().forEach(this::optimizeNode);

        // reset all optimize strategies
        optimizeStrategies.forEach(OptimizeStrategy::resetState);

        // loop through children
        ListIterator<ASTNode> listIterator = parentNode.getChildren().listIterator();
        while (listIterator.hasNext()) {
            ASTNode sentinel = listIterator.next();
            boolean sentinelIsLastNode = !listIterator.hasNext();

            for (OptimizeStrategy optimizeStrategy : optimizeStrategies) {
                OptimizeStrategyResult result = optimizeStrategy.processNode(sentinel, sentinelIsLastNode);
                switch (result.getState()) {
                    case NOT_ACCEPTED:
                        break;
                    case ACCEPTED:
                        sentinel.detach();
                        listIterator.remove();

                        boolean finished = false;
                        while (listIterator.hasNext()) {
                            ASTNode next = listIterator.next();
                            boolean nextIsLastNode = !listIterator.hasNext();

                            OptimizeStrategyNextResult nextResult = optimizeStrategy.processNextNode(next, nextIsLastNode);
                            switch (nextResult.getState()) {
                                case ACCEPTED:
                                    next.detach();
                                    listIterator.remove();
                                    break;
                                case FINISHED:
                                    finished = true;
                                    listIterator.previous();

                                    optimizeStrategy.resetState();
                                    List<ASTNode> newNodes = nextResult.getNewNodes();
                                    newNodes.forEach(node -> {
                                        node.setParent(parentNode);
                                        listIterator.add(node);
                                    });
                                    break;
                            }
                            if (finished) {
                                break;
                            }
                        }
                        if (!finished) {
                            throw new IllegalStateException("Optimize strategy " + optimizeStrategy + " ended because there are no more nodes left, but it is not yet finished");
                        }
                        break;
                    case FINISHED:
                        optimizeStrategy.resetState();
                        List<ASTNode> newNodes = result.getNewNodes();
                        newNodes.forEach(node -> node.setParent(parentNode));
                        if (newNodes.isEmpty()) {
                            sentinel.detach();
                            listIterator.remove();
                        }
                        else {
                            ListIterator<ASTNode> newNodesListIterator = newNodes.listIterator();
                            listIterator.set(newNodesListIterator.next());
                            while (newNodesListIterator.hasNext()) {
                                listIterator.add(newNodesListIterator.next());
                            }
                        }
                }
            }
        }
    }
}
