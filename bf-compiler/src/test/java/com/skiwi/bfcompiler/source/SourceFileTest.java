package com.skiwi.bfcompiler.source;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Frank van Heeswijk
 */
public class SourceFileTest {
    @Test
    public void testGetTokenStream() throws IOException, URISyntaxException {
        Path file = Paths.get(getClass().getClassLoader().getResource("hello-world.bf").toURI());
        SourceFile sourceFile = new SourceFile(file);

        assertArrayEquals(sourceFile.getTokenStream().toArray(),
            new int[] { 62, 43, 43, 43, 43, 43, 43, 43, 43, 91, 45, 60, 43, 43, 43, 43, 43, 43, 43, 43, 43, 62, 93, 60, 46, 62, 62, 43, 62, 45, 91, 43, 93, 43, 43, 62, 43, 43, 62, 43, 43, 43, 91, 62, 91, 45, 62, 43, 43, 43, 60, 60, 43, 43, 43, 62, 93, 60, 60, 93, 62, 45, 45, 45, 45, 45, 46, 62, 45, 62, 43, 43, 43, 46, 46, 43, 43, 43, 46, 62, 45, 46, 60, 60, 43, 91, 62, 91, 43, 62, 43, 93, 62, 62, 93, 60, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 46, 62, 62, 46, 43, 43, 43, 46, 45, 45, 45, 45, 45, 45, 46, 45, 45, 45, 45, 45, 45, 45, 45, 46, 62, 43, 46, 62, 43, 46 });
    }
}