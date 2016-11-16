package com.skiwi.bfcompiler.source;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

/**
 * @author Frank van Heeswijk
 */
public class SourceFile {
    private final Path path;

    public SourceFile(final Path path) {
        this.path = path;
    }

    public IntStream getTokenStream() throws IOException {
        return Files.lines(path, StandardCharsets.UTF_8)
            .flatMapToInt(CharSequence::chars);
    }
}
