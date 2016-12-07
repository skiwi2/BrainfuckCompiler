package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryAddMultipleOfStoredValueExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryInputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryLoopExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryOutputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryPointerChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemorySetValueExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryStoreCurrentValueExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Frank van Heeswijk
 */
public class MemoryLoopOptimizeStrategy implements OptimizeStrategy {
    @Override
    public OptimizeStrategyResult processNode(final ASTNode node, final boolean isLastNode) {
        Expression expression = node.getExpression();

        if (!(expression instanceof MemoryLoopExpression)) {
            return OptimizeStrategyResult.notAccepted();
        }
        else {
            try {
                return OptimizeStrategyResult.finished(createNewNodes(node));
            } catch (UnsupportedOperationException ex) {
                return OptimizeStrategyResult.notAccepted();
            }
        }
    }

    @Override
    public OptimizeStrategyNextResult processNextNode(final ASTNode node, final boolean isLastNode) {
        throw new IllegalStateException("MemoryLoopOptimizeStrategy processes only a single MemoryLoop node");
    }

    @Override
    public void resetState() {

    }

    @Override
    public String toString() {
        return "MemoryLoopOptimizeStrategy";
    }

    private List<ASTNode> createNewNodes(final ASTNode node) {
        List<ASTNode> newNodes = new ArrayList<>();

        LoopChangeMask loopChangeMask = new LoopChangeMask(node);
        loopChangeMask.calculateChangeMask();

        if (!loopChangeMask.isValid() || !loopChangeMask.isNonMoving()) {
            throw new UnsupportedOperationException();  //temporary fix
        }

        // TODO probably need to use IDIV to check if zeroCellMask != -1 or 0
        // TODO check REM and fail program if not divisible, because else would be an infinite loop, correct?

        int zeroCellMask = loopChangeMask.getMask().get(loopChangeMask.getBeginIndex());

        if (zeroCellMask != -1) {
            // TODO this case is not handled yet
            throw new UnsupportedOperationException();  //temporary fix
        }

        // TODO zeroCellMask == 0 is also an issue already

        // TODO try by modifying Hello World and making pointers double the width

        newNodes.add(new ASTNode(new MemoryStoreCurrentValueExpression()));
        newNodes.add(ASTNode.newWithChild(new MemorySetValueExpression(), new ASTNode(new IntegerExpression(0))));

        ListIterator<Integer> maskIterator = loopChangeMask.getMask().listIterator(loopChangeMask.getBeginIndex());

        // Set cells left of the begin index
        int actualPointerLeft = 0;
        int pointerLeftCount = 1;

        while (maskIterator.hasPrevious()) {
            int multiple = maskIterator.previous();
            if (multiple == 0) {
                pointerLeftCount++;
                continue;
            }

            ASTNode newPointerLeftNode = ASTNode.newWithChild(new MemoryPointerChangeExpression(),
                new ASTNode(new IntegerExpression(-pointerLeftCount)));
            newNodes.add(newPointerLeftNode);

            // Ensure multiple is in range of [0, 255], because else it should give errors with MUL, although it does not seem to give errors (yet)
            int multipleMod256 = ((multiple % 256) + 256) % 256;

            ASTNode newMemoryAddMultipleNode = ASTNode.newWithChild(new MemoryAddMultipleOfStoredValueExpression(),
                new ASTNode(new IntegerExpression(multipleMod256)));
            newNodes.add(newMemoryAddMultipleNode);

            actualPointerLeft += pointerLeftCount;
            pointerLeftCount = 1;
        }

        // Set iterator back so that the cell after the begin index is next
        while (maskIterator.nextIndex() < loopChangeMask.getBeginIndex() + 1) {
            maskIterator.next();
        }

        // Move to the begin index
        ASTNode newTempPointerRightNode = ASTNode.newWithChild(new MemoryPointerChangeExpression(),
            new ASTNode(new IntegerExpression(actualPointerLeft)));
        newNodes.add(newTempPointerRightNode);

        // If there is a next node, continue
        if (maskIterator.hasNext()) {
            int actualPointerRight = 0;
            int pointerRightCount = 1;

            // Set cells right of the begin index
            while (maskIterator.hasNext()) {
                int multiple = maskIterator.next();
                if (multiple == 0) {
                    pointerRightCount++;
                    continue;
                }

                ASTNode newPointerRightNode = ASTNode.newWithChild(new MemoryPointerChangeExpression(),
                    new ASTNode(new IntegerExpression(pointerRightCount)));
                newNodes.add(newPointerRightNode);

                ASTNode newMemoryAddMultipleNode = ASTNode.newWithChild(new MemoryAddMultipleOfStoredValueExpression(),
                    new ASTNode(new IntegerExpression(multiple)));
                newNodes.add(newMemoryAddMultipleNode);

                actualPointerRight += pointerRightCount;
                pointerRightCount = 1;
            }

            // Set pointer back to the begin index
            ASTNode newPointerRightNode = ASTNode.newWithChild(new MemoryPointerChangeExpression(),
                new ASTNode(new IntegerExpression(-actualPointerRight)));
            newNodes.add(newPointerRightNode);
        }

        return newNodes;
    }

    private static class LoopChangeMask {
        private final ASTNode node;
        private final List<Integer> mask = new LinkedList<>();

        private boolean valid = true;

        private int beginIndex = 0;
        private int endIndex = 0;

        private LoopChangeMask(final ASTNode node) {
            this.node = node;
        }

        private void calculateChangeMask() {
            ListIterator<Integer> maskIterator = mask.listIterator();
            maskIterator.add(0);
            boolean movingForward = true;
            int totalPointerChange = 0;
            int leftAdded = 0;
            for (ASTNode child : node.getChildren()) {
                Expression expression = child.getExpression();
                if (expression instanceof MemoryInputExpression || expression instanceof MemoryOutputExpression || expression instanceof MemoryLoopExpression
                    || expression instanceof MemorySetValueExpression || expression instanceof MemoryStoreCurrentValueExpression
                    || expression instanceof MemoryAddMultipleOfStoredValueExpression) {
                    valid = false;
                    return;
                }
                else if (expression instanceof MemoryPointerChangeExpression) {
                    int amount = child.getChildExpression(0, IntegerExpression.class).getInteger();
                    totalPointerChange += amount;
                    if (amount > 0) {
                        movingForward = true;
                        for (int i = 0; i < amount; i++) {
                            if (maskIterator.hasNext()) {
                                maskIterator.next();
                            }
                            else {
                                maskIterator.add(0);
                            }
                        }
                    }
                    else if (amount < 0) {
                        int plusOneIfMovingForward = (movingForward) ? 1 : 0; //add one if we are moving forward, because the pointer is after the next node
                        movingForward = false;
                        for (int i = 0; i < -amount + plusOneIfMovingForward; i++) {
                            if (maskIterator.hasPrevious()) {
                                maskIterator.previous();
                            }
                            else {
                                leftAdded++;
                                maskIterator.add(0);
                                maskIterator.previous();
                            }
                        }
                    }
                }
                else if (expression instanceof MemoryValueChangeExpression) {
                    int amount = child.getChildExpression(0, IntegerExpression.class).getInteger();
                    Integer lastValue = (movingForward) ? maskIterator.previous() : maskIterator.next();
                    maskIterator.set(lastValue + amount);
                    if (movingForward) {
                        maskIterator.next();
                    }
                    else {
                        maskIterator.previous();
                    }
                }
            }
            beginIndex = leftAdded;
            endIndex = leftAdded + totalPointerChange;
        }

        public List<Integer> getMask() {
            return mask;
        }

        public boolean isValid() {
            return valid;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        private boolean isNonMoving() {
            return (beginIndex == endIndex);
        }
    }
}
