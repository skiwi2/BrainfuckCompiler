package com.skiwi.bfcompiler.generators;

import com.skiwi.bfcompiler.analyzers.LexicalAnalyzer;
import com.skiwi.bfcompiler.analyzers.SyntaxAnalyzer;
import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.options.BFOptions;
import com.skiwi.bfcompiler.source.SourceFile;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class TargetCodeGeneratorTest {
    @Test
    public void testGenerateAST() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("hello-world.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
        IntermediateCodeGenerator intermediateCodeGenerator = new IntermediateCodeGenerator();
        BFOptions bfOptions = new BFOptions.Builder().memoryCellAmount(30000).build();
        TargetCodeGenerator targetCodeGenerator = new TargetCodeGenerator(bfOptions);

        AST targetAst = targetCodeGenerator.generateAST(intermediateCodeGenerator.generateAST(syntaxAnalyzer.getAST(lexicalAnalyzer.getTokens())));
        assertNotNull(targetAst);
        //TODO write comprehensive test at a later point
    }
}