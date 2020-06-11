package com.airgap.airgapagent.algo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/10/2020.
 */
class TokenizerTest {

    private final Tokenizer tokenizer = new Tokenizer();

    @Test
    void shouldParseSimpleString() {
        String source = "abcd";
        List<Token> result = tokenizer.parse(source);
        Assertions.assertEquals(CharToken.toList("abcd"), result);
    }


    @Test
    void shouldParsePatterns() {
        String source = "\\d\\?\\a";
        List<Token> result = tokenizer.parse(source);
        Assertions.assertEquals(SymbolToken.toList(SymbolType.DIGIT, SymbolType.ANY, SymbolType.LETTER), result);
    }

    @Test
    void shouldThrowAnExceptionDueToNonEndedEscape() {
        String source = "\\";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tokenizer.parse(source);
        });
    }


    @Test
    void shouldThrowAnExceptionDueToUnknownEscape() {
        String source = "\\p";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tokenizer.parse(source);
        });
    }
}

