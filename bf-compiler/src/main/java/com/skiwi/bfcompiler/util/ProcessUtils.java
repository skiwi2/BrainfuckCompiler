package com.skiwi.bfcompiler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class ProcessUtils {
    private ProcessUtils() {
        throw new UnsupportedOperationException();
    }

    public static List<String> toInputStream(final InputStream inputStream) throws IOException {
        List<String> output = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.add(line);
            }
        }
        return output;
    }
}
