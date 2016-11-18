package com.skiwi.bfcompiler.generators;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.RootExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryInputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryOutputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryPointerChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryLoopExpression;
import com.skiwi.bfcompiler.expression.source.DecrementExpression;
import com.skiwi.bfcompiler.expression.source.IncrementExpression;
import com.skiwi.bfcompiler.expression.source.InputExpression;
import com.skiwi.bfcompiler.expression.source.LoopExpression;
import com.skiwi.bfcompiler.expression.source.OutputExpression;
import com.skiwi.bfcompiler.expression.source.PointerLeftExpression;
import com.skiwi.bfcompiler.expression.source.PointerRightExpression;

/**
 * @author Frank van Heeswijk
 */
public class IntermediateCodeGenerator {
    public AST generateAST(final AST sourceAST) {
        return new AST(sourceToIntermediateNode(sourceAST.getRoot()));
    }

    private ASTNode sourceToIntermediateNode(final ASTNode node) {
        Expression expression = node.getExpression();
        if (expression instanceof RootExpression) {
            return ASTNode.newWithMappedChildren(new RootExpression(), node.getChildren(), this::sourceToIntermediateNode);
        }
        if (expression instanceof PointerRightExpression) {
            return ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1)));
        }
        if (expression instanceof PointerLeftExpression) {
            return ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1)));
        }
        if (expression instanceof IncrementExpression) {
            return ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1)));
        }
        if (expression instanceof DecrementExpression) {
            return ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1)));
        }
        if (expression instanceof OutputExpression) {
            return new ASTNode(new MemoryOutputExpression());
        }
        if (expression instanceof InputExpression) {
            return new ASTNode(new MemoryInputExpression());
        }
        if (expression instanceof LoopExpression) {
            return ASTNode.newWithMappedChildren(new MemoryLoopExpression(), node.getChildren(), this::sourceToIntermediateNode);
        }
        throw new IllegalArgumentException("Node with unknown expression type: " + expression);
    }
}
