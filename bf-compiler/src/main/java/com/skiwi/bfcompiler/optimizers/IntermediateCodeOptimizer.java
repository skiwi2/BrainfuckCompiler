package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.ast.AST;
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

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

        ListIterator<ASTNode> listIterator = node.getChildren().listIterator();
        while (listIterator.hasNext()) {
            ASTNode sentinel = listIterator.next();

            // Find consecutive MemoryPointerChangeExpression and MemoryValueChangeExpression nodes
            if (sentinel.getExpression() instanceof MemoryPointerChangeExpression || sentinel.getExpression() instanceof MemoryValueChangeExpression) {
                int consecutiveNodes = 1;
                int changeAmount = sentinel.getChildExpression(0, IntegerExpression.class).getInteger();

                while (listIterator.hasNext()) {
                    ASTNode next = listIterator.next();
                    if (!next.getExpression().getClass().equals(sentinel.getExpression().getClass())) {
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
            // Find optimizable MemoryLoopExpression nodes
            else if (sentinel.getExpression() instanceof MemoryLoopExpression) {
                LoopChangeMask loopChangeMask = new LoopChangeMask(sentinel);
                loopChangeMask.calculateChangeMask();
                if (loopChangeMask.isValid()) {
                    // Only handle non-moving change masks for now
                    if (loopChangeMask.isNonMoving()) {
                        // TODO probably need to use IDIV to check if zeroCellMask != -1 or 0
                        // TODO check REM and fail program if not divisible, because else would be an infinite loop, correct?

                        int zeroCellMask = loopChangeMask.getMask().get(loopChangeMask.getBeginIndex());

                        if (zeroCellMask != -1) {
                            // TODO this case is not handled yet
                            continue;
                        }

                        // TODO zeroCellMask == 0 is also an issue already

                        // TODO try by modifying Hello World and making pointers double the width

                        listIterator.previous();

                        ASTNode newStoreCurrentValueNode = new ASTNode(new MemoryStoreCurrentValueExpression());
                        newStoreCurrentValueNode.setParent(node);
                        listIterator.set(newStoreCurrentValueNode);

                        listIterator.next();

                        ASTNode newSetValueNode = ASTNode.newWithChild(new MemorySetValueExpression(), new ASTNode(new IntegerExpression(0)));
                        newSetValueNode.setParent(node);
                        listIterator.add(newSetValueNode);

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
                            newPointerLeftNode.setParent(node);
                            listIterator.add(newPointerLeftNode);

                            // Ensure multiple is in range of [0, 255], because else it should give errors with MUL, although it does not seem to give errors (yet)
                            int multipleMod256 = ((multiple % 256) + 256) % 256;

                            ASTNode newMemoryAddMultipleNode = ASTNode.newWithChild(new MemoryAddMultipleOfStoredValueExpression(),
                                new ASTNode(new IntegerExpression(multipleMod256)));
                            newMemoryAddMultipleNode.setParent(node);
                            listIterator.add(newMemoryAddMultipleNode);

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
                        newTempPointerRightNode.setParent(node);
                        listIterator.add(newTempPointerRightNode);

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
                                newPointerRightNode.setParent(node);
                                listIterator.add(newPointerRightNode);

                                ASTNode newMemoryAddMultipleNode = ASTNode.newWithChild(new MemoryAddMultipleOfStoredValueExpression(),
                                    new ASTNode(new IntegerExpression(multiple)));
                                newMemoryAddMultipleNode.setParent(node);
                                listIterator.add(newMemoryAddMultipleNode);

                                actualPointerRight += pointerRightCount;
                                pointerRightCount = 1;
                            }

                            // Set pointer back to the begin index
                            ASTNode newPointerRightNode = ASTNode.newWithChild(new MemoryPointerChangeExpression(),
                                new ASTNode(new IntegerExpression(-actualPointerRight)));
                            newPointerRightNode.setParent(node);
                            listIterator.add(newPointerRightNode);
                        }
                    }
                }
            }
        }
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
