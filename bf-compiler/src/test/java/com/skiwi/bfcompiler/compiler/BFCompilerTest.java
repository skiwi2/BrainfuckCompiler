package com.skiwi.bfcompiler.compiler;

import com.skiwi.bfcompiler.analyzers.LexicalAnalyzer;
import com.skiwi.bfcompiler.analyzers.SyntaxAnalyzer;
import com.skiwi.bfcompiler.generators.IntermediateCodeGenerator;
import com.skiwi.bfcompiler.generators.TargetCodeGenerator;
import com.skiwi.bfcompiler.options.BFOptions;
import com.skiwi.bfcompiler.source.SourceFile;
import com.skiwi.bfcompiler.util.ProcessUtils;
import com.skiwi.bfcompiler.writer.TargetCodeWriter;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class BFCompilerTest {

    @Test
    public void testCompile() throws Exception {
        Path file = Paths.get(getClass().getClassLoader().getResource("hello-world.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sourceFile);
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
        IntermediateCodeGenerator intermediateCodeGenerator = new IntermediateCodeGenerator();
        BFOptions bfOptions = new BFOptions.Builder().memoryCellAmount(30000).build();
        TargetCodeGenerator targetCodeGenerator = new TargetCodeGenerator(bfOptions);
        TargetCodeWriter targetCodeWriter = new TargetCodeWriter();

        Stream<String> targetCode = targetCodeWriter.write(targetCodeGenerator.generateAST(intermediateCodeGenerator.generateAST(syntaxAnalyzer.getAST(lexicalAnalyzer.getTokens()))));

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