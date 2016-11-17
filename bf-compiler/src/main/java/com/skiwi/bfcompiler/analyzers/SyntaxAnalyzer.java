package com.skiwi.bfcompiler.analyzers;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.exceptions.InvalidSyntaxException;
import com.skiwi.bfcompiler.expression.source.DecrementExpression;
import com.skiwi.bfcompiler.expression.RootExpression;
import com.skiwi.bfcompiler.expression.source.IncrementExpression;
import com.skiwi.bfcompiler.expression.source.InputExpression;
import com.skiwi.bfcompiler.expression.source.LoopExpression;
import com.skiwi.bfcompiler.expression.source.OutputExpression;
import com.skiwi.bfcompiler.expression.source.PointerLeftExpression;
import com.skiwi.bfcompiler.expression.source.PointerRightExpression;
import com.skiwi.bfcompiler.options.BFOptions;
import com.skiwi.bfcompiler.tokens.Token;

import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class SyntaxAnalyzer {
    public AST getAST(final Stream<Token> tokens) {
        ASTNode rootNode = new ASTNode(new RootExpression());
        AST ast = new AST(rootNode);
        ASTNode currentNode = rootNode;

        int loopCounter = 0;

        for (Token token : (Iterable<Token>)tokens::iterator) {
            ASTNode newNode = null;
            ASTNode nextNode = null;

            switch (token) {
                case POINTER_RIGHT:
                    newNode = new ASTNode(new PointerRightExpression());
                    break;
                case POINTER_LEFT:
                    newNode = new ASTNode(new PointerLeftExpression());
                    break;
                case INCREMENT:
                    newNode = new ASTNode(new IncrementExpression());
                    break;
                case DECREMENT:
                    newNode = new ASTNode(new DecrementExpression());
                    break;
                case OUTPUT:
                    newNode = new ASTNode(new OutputExpression());
                    break;
                case INPUT:
                    newNode = new ASTNode(new InputExpression());
                    break;
                case JUMP_PAST:
                    loopCounter++;
                    newNode = new ASTNode(new LoopExpression());
                    nextNode = newNode;
                    break;
                case JUMP_BACK:
                    if (loopCounter == 0) {
                        throw new InvalidSyntaxException("A ] has been found without a matching opening [.");
                    }
                    loopCounter--;
                    nextNode = currentNode.getParent();
                    break;
            }

            if (newNode != null) {
                currentNode.addChild(newNode);
            }

            if (nextNode != null) {
                currentNode = nextNode;
            }
        }

        if (loopCounter > 0) {
            throw new InvalidSyntaxException("A ] is missing.");
        }

        return ast;
    }
}
