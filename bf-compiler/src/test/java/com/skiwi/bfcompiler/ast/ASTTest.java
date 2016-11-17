package com.skiwi.bfcompiler.ast;

import com.skiwi.bfcompiler.analyzers.LexicalAnalyzer;
import com.skiwi.bfcompiler.analyzers.SyntaxAnalyzer;
import com.skiwi.bfcompiler.expression.RootExpression;
import com.skiwi.bfcompiler.expression.source.DecrementExpression;
import com.skiwi.bfcompiler.expression.source.IncrementExpression;
import com.skiwi.bfcompiler.expression.source.LoopExpression;
import com.skiwi.bfcompiler.expression.source.OutputExpression;
import com.skiwi.bfcompiler.expression.source.PointerLeftExpression;
import com.skiwi.bfcompiler.expression.source.PointerRightExpression;
import com.skiwi.bfcompiler.source.SourceFile;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class ASTTest {
    @Test
    public void testPrettyPrintStream() throws Exception {
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

        List<String> expectedOutput = Arrays.asList(
            "├── Root",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Loop",
            "│   │   ├── Decrement",
            "│   │   ├── PointerLeft",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   ├── Increment",
            "│   │   └── PointerRight",
            "│   ├── PointerLeft",
            "│   ├── Output",
            "│   ├── PointerRight",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   ├── PointerRight",
            "│   ├── Decrement",
            "│   ├── Loop",
            "│   │   └── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Loop",
            "│   │   ├── PointerRight",
            "│   │   ├── Loop",
            "│   │   │   ├── Decrement",
            "│   │   │   ├── PointerRight",
            "│   │   │   ├── Increment",
            "│   │   │   ├── Increment",
            "│   │   │   ├── Increment",
            "│   │   │   ├── PointerLeft",
            "│   │   │   ├── PointerLeft",
            "│   │   │   ├── Increment",
            "│   │   │   ├── Increment",
            "│   │   │   ├── Increment",
            "│   │   │   └── PointerRight",
            "│   │   ├── PointerLeft",
            "│   │   └── PointerLeft",
            "│   ├── PointerRight",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Output",
            "│   ├── PointerRight",
            "│   ├── Decrement",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Output",
            "│   ├── Output",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Output",
            "│   ├── PointerRight",
            "│   ├── Decrement",
            "│   ├── Output",
            "│   ├── PointerLeft",
            "│   ├── PointerLeft",
            "│   ├── Increment",
            "│   ├── Loop",
            "│   │   ├── PointerRight",
            "│   │   ├── Loop",
            "│   │   │   ├── Increment",
            "│   │   │   ├── PointerRight",
            "│   │   │   └── Increment",
            "│   │   ├── PointerRight",
            "│   │   └── PointerRight",
            "│   ├── PointerLeft",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Output",
            "│   ├── PointerRight",
            "│   ├── PointerRight",
            "│   ├── Output",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Increment",
            "│   ├── Output",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Output",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Decrement",
            "│   ├── Output",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   ├── Output",
            "│   ├── PointerRight",
            "│   ├── Increment",
            "│   └── Output"
        );

        assertEquals(expectedOutput, ast.prettyPrintStream().collect(Collectors.toList()));
    }
}