package com.skiwi.bfcompiler.optimizers;

import com.skiwi.bfcompiler.analyzers.LexicalAnalyzer;
import com.skiwi.bfcompiler.analyzers.SyntaxAnalyzer;
import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.compiler.BFCompiler;
import com.skiwi.bfcompiler.generators.IntermediateCodeGenerator;
import com.skiwi.bfcompiler.generators.TargetCodeGenerator;
import com.skiwi.bfcompiler.options.BFOptions;
import com.skiwi.bfcompiler.source.SourceFile;
import com.skiwi.bfcompiler.util.ProcessUtils;
import com.skiwi.bfcompiler.writer.TargetCodeWriter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class IntermediateCodeOptimizerTest {

    @Test
    public void testOptimize() throws Exception {
        // Test optimize by compiling the hello-world program and checking if the output is still correct

        Path file = Paths.get(getClass().getClassLoader().getResource("hello-world.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
        IntermediateCodeGenerator intermediateCodeGenerator = new IntermediateCodeGenerator();
        IntermediateCodeOptimizer intermediateCodeOptimizer = new IntermediateCodeOptimizer(Arrays.asList(
            new MemoryValueOptimizeStrategy(),
            new MemoryPointerOptimizeStrategy(),
            new MemoryLoopOptimizeStrategy()
        ));
        BFOptions bfOptions = new BFOptions.Builder().memoryCellAmount(30000).build();
        TargetCodeGenerator targetCodeGenerator = new TargetCodeGenerator(bfOptions);
        TargetCodeWriter targetCodeWriter = new TargetCodeWriter();

        AST sourceAST = syntaxAnalyzer.getAST(lexicalAnalyzer.getTokens());
        AST intermediateAST = intermediateCodeGenerator.generateAST(sourceAST);
        intermediateCodeOptimizer.optimize(intermediateAST);
        AST targetAST = targetCodeGenerator.generateAST(intermediateAST);
        Stream<String> targetCode = targetCodeWriter.write(targetAST);

        BFCompiler bfCompiler = new BFCompiler();
        Path tempDirectory = Files.createTempDirectory("bf-compiler-test");
        bfCompiler.compile(targetCode, tempDirectory, "hello-world");

        Process process = new ProcessBuilder("hello-world").directory(tempDirectory.toFile()).redirectErrorStream(true).start();
        List<String> processOutput = ProcessUtils.toInputStream(process.getInputStream());
        assertEquals("Hello World!", String.join("", processOutput).trim());

        Files.walkFileTree(tempDirectory, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}