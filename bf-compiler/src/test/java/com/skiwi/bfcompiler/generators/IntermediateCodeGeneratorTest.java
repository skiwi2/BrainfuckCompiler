package com.skiwi.bfcompiler.generators;

import com.skiwi.bfcompiler.analyzers.LexicalAnalyzer;
import com.skiwi.bfcompiler.analyzers.SyntaxAnalyzer;
import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.RootExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryOutputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryPointerChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryLoopExpression;
import com.skiwi.bfcompiler.source.SourceFile;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class IntermediateCodeGeneratorTest {
    @Test
    public void testGenerateAST() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("hello-world.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
        IntermediateCodeGenerator intermediateCodeGenerator = new IntermediateCodeGenerator();

        ASTNode rootNode = new ASTNode(new RootExpression());
        AST ast = new AST(rootNode);

        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryLoopExpression()));

        ASTNode loopNode1 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));

        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryLoopExpression()));

        ASTNode loopNode2 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode2.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));

        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryLoopExpression()));

        ASTNode loopNode3 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode3.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3.addChild(new ASTNode(new MemoryLoopExpression()));

        ASTNode loopNode3Inner1 = loopNode3.getChildren().get(loopNode3.getChildren().size() - 1);
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode3Inner1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));

        loopNode3.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        loopNode3.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));

        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryLoopExpression()));

        ASTNode loopNode4 = rootNode.getChildren().get(rootNode.getChildren().size() - 1);
        loopNode4.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode4.addChild(new ASTNode(new MemoryLoopExpression()));

        ASTNode loopNode4Inner1 = loopNode4.getChildren().get(loopNode4.getChildren().size() - 1);
        loopNode4Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode4Inner1.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode4Inner1.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));

        loopNode4.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        loopNode4.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));

        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(-1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));
        rootNode.addChild(ASTNode.newWithChild(new MemoryPointerChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(ASTNode.newWithChild(new MemoryValueChangeExpression(), new ASTNode(new IntegerExpression(1))));
        rootNode.addChild(new ASTNode(new MemoryOutputExpression()));

        assertEquals(ast, intermediateCodeGenerator.generateAST(syntaxAnalyzer.getAST(lexicalAnalyzer.getTokens())));
    }
}