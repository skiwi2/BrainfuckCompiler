package com.skiwi.bfcompiler.tokens;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Frank van Heeswijk
 */
public enum Token {
    POINTER_RIGHT('>'),
    POINTER_LEFT('<'),
    INCREMENT('+'),
    DECREMENT('-'),
    OUTPUT('.'),
    INPUT(','),
    JUMP_PAST('['),
    JUMP_BACK(']');

    private static final Map<Character, Token> CHARACTER_TOKEN_MAP = Arrays.stream(Token.values())
        .collect(Collectors.toMap(Token::getCharacter, i -> i));

    public static Token forCharacter(final char character) {
        return CHARACTER_TOKEN_MAP.get(character);
    }

    private final char character;

    Token(final char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
