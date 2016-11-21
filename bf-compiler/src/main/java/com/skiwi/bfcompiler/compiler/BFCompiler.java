package com.skiwi.bfcompiler.compiler;

import com.skiwi.bfcompiler.util.ProcessUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class BFCompiler {
    public void compile(final Stream<String> targetCode, Path workingDirectory, String programName) throws IOException, InterruptedException {
        Path assemblyFile = workingDirectory.resolve(programName + ".asm");
        Files.write(assemblyFile, (Iterable<String>)targetCode::iterator, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING);

        ProcessBuilder processBuilder = new ProcessBuilder().directory(workingDirectory.toFile()).redirectErrorStream(true);

        Process nasmProcess = processBuilder.command("nasm", "-f", "win32", assemblyFile.getFileName().toString()).start();
        nasmProcess.waitFor();
        List<String> nasmOutput = ProcessUtils.toInputStream(nasmProcess.getInputStream());
        if (!nasmOutput.isEmpty()) {
            throw new IllegalStateException("Compiling failed when invoking NASM: " + String.join(System.lineSeparator(), nasmOutput));
        }

        Process gccProcess = processBuilder.command("gcc", "-o", programName, programName + ".obj").start();
        gccProcess.waitFor();
        if (!nasmOutput.isEmpty()) {
            throw new IllegalStateException("Compiling failed when invoking GCC: " + String.join(System.lineSeparator(), nasmOutput));
        }
    }
}
