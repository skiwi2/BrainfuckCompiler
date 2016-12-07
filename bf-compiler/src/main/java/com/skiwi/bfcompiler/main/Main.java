package com.skiwi.bfcompiler.main;

import com.skiwi.bfcompiler.analyzers.LexicalAnalyzer;
import com.skiwi.bfcompiler.analyzers.SyntaxAnalyzer;
import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.compiler.BFCompiler;
import com.skiwi.bfcompiler.generators.IntermediateCodeGenerator;
import com.skiwi.bfcompiler.generators.TargetCodeGenerator;
import com.skiwi.bfcompiler.optimizers.IntermediateCodeOptimizer;
import com.skiwi.bfcompiler.optimizers.MemoryLoopOptimizeStrategy;
import com.skiwi.bfcompiler.optimizers.MemoryPointerOptimizeStrategy;
import com.skiwi.bfcompiler.optimizers.MemoryValueOptimizeStrategy;
import com.skiwi.bfcompiler.options.BFOptions;
import com.skiwi.bfcompiler.source.SourceFile;
import com.skiwi.bfcompiler.writer.TargetCodeWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author Frank van Heeswijk
 */
public class Main {
    public static void main(final String[] args) throws IOException, InterruptedException {
        Path currentWorkingDirectory = Paths.get(".").toAbsolutePath().normalize();

        if (args.length == 0) {
            throw new IllegalStateException("Expected the Brainfuck source file as first argument.");
        }

        String sourceFileString = args[0];
        Path sourceFile = currentWorkingDirectory.resolve(sourceFileString);

        if (!Files.exists(sourceFile)) {
            throw new IllegalStateException("The source file does not exist.");
        }

        String completeFileName = sourceFile.getFileName().toString();
        int dotIndex = completeFileName.lastIndexOf('.');
        String fileName = (dotIndex > 0) ? completeFileName.substring(0, dotIndex) : completeFileName;

        SourceFile bfSourceFile = new SourceFile(sourceFile);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(bfSourceFile);
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

        AST sourceAst = syntaxAnalyzer.getAST(lexicalAnalyzer.getTokens());
        AST intermediateAst = intermediateCodeGenerator.generateAST(sourceAst);
        intermediateCodeOptimizer.optimize(intermediateAst);
        AST targetAst = targetCodeGenerator.generateAST(intermediateAst);

        BFCompiler bfCompiler = new BFCompiler();
        bfCompiler.compile(targetCodeWriter.write(targetAst), currentWorkingDirectory, fileName);
    }
}
