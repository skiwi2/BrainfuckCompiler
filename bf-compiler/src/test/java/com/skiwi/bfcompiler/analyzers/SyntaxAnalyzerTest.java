package com.skiwi.bfcompiler.analyzers;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.exceptions.InvalidSyntaxException;
import com.skiwi.bfcompiler.expression.RootExpression;
import com.skiwi.bfcompiler.expression.source.DecrementExpression;
import com.skiwi.bfcompiler.expression.source.IncrementExpression;
import com.skiwi.bfcompiler.expression.source.LoopExpression;
import com.skiwi.bfcompiler.expression.source.OutputExpression;
import com.skiwi.bfcompiler.expression.source.PointerLeftExpression;
import com.skiwi.bfcompiler.expression.source.PointerRightExpression;
import com.skiwi.bfcompiler.source.SourceFile;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class SyntaxAnalyzerTest {
    @Test
    public void testGetAST() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("hello-world.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.getTokens());

        ASTNode rootNode = new ASTNode(new RootExpression());
        AST ast = new AST(rootNode);

        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new LoopExpression()));

        ASTNode loopNode1 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode1.addChild(new ASTNode(new DecrementExpression()));
        loopNode1.addChild(new ASTNode(new PointerLeftExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new IncrementExpression()));
        loopNode1.addChild(new ASTNode(new PointerRightExpression()));

        rootNode.addChild(new ASTNode(new PointerLeftExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new LoopExpression()));

        ASTNode loopNode2 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode2.addChild(new ASTNode(new IncrementExpression()));

        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new LoopExpression()));

        ASTNode loopNode3 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode3.addChild(new ASTNode(new PointerRightExpression()));
        loopNode3.addChild(new ASTNode(new LoopExpression()));

        ASTNode loopNode3Inner1 = loopNode3.getChildren().get(loopNode3.getChildren().size() - 1);
        loopNode3Inner1.addChild(new ASTNode(new DecrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new PointerRightExpression()));
        loopNode3Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new PointerLeftExpression()));
        loopNode3Inner1.addChild(new ASTNode(new PointerLeftExpression()));
        loopNode3Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode3Inner1.addChild(new ASTNode(new PointerRightExpression()));

        loopNode3.addChild(new ASTNode(new PointerLeftExpression()));
        loopNode3.addChild(new ASTNode(new PointerLeftExpression()));

        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerLeftExpression()));
        rootNode.addChild(new ASTNode(new PointerLeftExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new LoopExpression()));

        ASTNode loopNode4 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode4.addChild(new ASTNode(new PointerRightExpression()));
        loopNode4.addChild(new ASTNode(new LoopExpression()));

        ASTNode loopNode4Inner1 = loopNode4.getChildren().get(loopNode4.getChildren().size() - 1);
        loopNode4Inner1.addChild(new ASTNode(new IncrementExpression()));
        loopNode4Inner1.addChild(new ASTNode(new PointerRightExpression()));
        loopNode4Inner1.addChild(new ASTNode(new IncrementExpression()));

        loopNode4.addChild(new ASTNode(new PointerRightExpression()));
        loopNode4.addChild(new ASTNode(new PointerRightExpression()));

        rootNode.addChild(new ASTNode(new PointerLeftExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new DecrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));
        rootNode.addChild(new ASTNode(new PointerRightExpression()));
        rootNode.addChild(new ASTNode(new IncrementExpression()));
        rootNode.addChild(new ASTNode(new OutputExpression()));

        assertEquals(ast, syntaxAnalyzer.getAST());
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testGetASTJumpBack() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("invalid-syntax-jump-back.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.getTokens());

        assertNotNull(syntaxAnalyzer.getAST());
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testGetASTJumpBackInLoop() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("invalid-syntax-jump-back-in-loop.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.getTokens());

        assertNotNull(syntaxAnalyzer.getAST());
    }

    @Test(expected = InvalidSyntaxException.class)
    public void testGetASTJumpPastNoLoop() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("invalid-syntax-jump-past-no-loop.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.getTokens());

        assertNotNull(syntaxAnalyzer.getAST());
    }
}