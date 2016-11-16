package com.skiwi.bfcompiler.analyzers;

import com.skiwi.bfcompiler.source.SourceFile;
import com.skiwi.bfcompiler.tokens.Token;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class LexicalAnalyzer {
    private final SourceFile sourceFile;

    public LexicalAnalyzer(final SourceFile sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Stream<Token> getTokens() throws IOException {
        return sourceFile.getCharacterStream()
            .mapToObj(character -> Token.forCharacter((char)character))
            .filter(token -> token != null);
    }
}
