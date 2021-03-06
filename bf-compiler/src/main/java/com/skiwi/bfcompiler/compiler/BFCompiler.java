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
        Files.deleteIfExists(assemblyFile);
        Files.write(assemblyFile, (Iterable<String>)targetCode::iterator, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);

        // important!
        // we need to read both the output and error stream because the native OS may provide a limited buffer size and then the process.waitFor() call will never return
        // we also need to use redirectErrorStream(true) if we want to keep this on a single thread
        ProcessBuilder processBuilder = new ProcessBuilder().directory(workingDirectory.toFile()).redirectErrorStream(true);

        Process nasmProcess = processBuilder.command("nasm", "-f", "win32", assemblyFile.getFileName().toString()).start();
        List<String> nasmOutput = ProcessUtils.toInputStream(nasmProcess.getInputStream());
        nasmProcess.waitFor();
        if (!nasmOutput.isEmpty()) {
            throw new IllegalStateException("Compiling failed when invoking NASM: " + String.join(System.lineSeparator(), nasmOutput));
        }

        Process gccProcess = processBuilder.command("gcc", "-o", programName, programName + ".obj").start();
        List<String> gccOutput = ProcessUtils.toInputStream(gccProcess.getInputStream());
        gccProcess.waitFor();
        if (!gccOutput.isEmpty()) {
            throw new IllegalStateException("Compiling failed when invoking GCC: " + String.join(System.lineSeparator(), gccOutput));
        }
    }
}
