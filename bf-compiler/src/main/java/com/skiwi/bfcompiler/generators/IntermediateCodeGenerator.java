package com.skiwi.bfcompiler.generators;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
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
            ASTNode newNode = new ASTNode(new RootExpression());
            node.getChildren().forEach(n -> newNode.addChild(sourceToIntermediateNode(n)));
            return newNode;
        }
        else if (expression instanceof PointerRightExpression) {
            return new ASTNode(new MemoryPointerChangeExpression(1));
        }
        else if (expression instanceof PointerLeftExpression) {
            return new ASTNode(new MemoryPointerChangeExpression(-1));
        }
        else if (expression instanceof IncrementExpression) {
            return new ASTNode(new MemoryValueChangeExpression(1));
        }
        else if (expression instanceof DecrementExpression) {
            return new ASTNode(new MemoryValueChangeExpression(-1));
        }
        else if (expression instanceof OutputExpression) {
            return new ASTNode(new MemoryOutputExpression());
        }
        else if (expression instanceof InputExpression) {
            return new ASTNode(new MemoryInputExpression());
        }
        else if (expression instanceof LoopExpression) {
            ASTNode newNode = new ASTNode(new MemoryLoopExpression());
            node.getChildren().forEach(n -> newNode.addChild(sourceToIntermediateNode(n)));
            return newNode;
        }
        throw new IllegalArgumentException("Node with unknown expression type: " + expression);
    }
}
