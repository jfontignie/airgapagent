package com.airgap.airgapagent.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/10/2020.
 */
public class Tokenizer {

    private static final Map<Character, Supplier<Token>> escapeAction = Map.of(
            '\\', () -> new CharToken('\\'),
            'a', () -> new SymbolToken(SymbolType.LETTER),
            'd', () -> new SymbolToken(SymbolType.DIGIT),
            '?', () -> new SymbolToken(SymbolType.ANY)
    );

    public List<Token> parse(String string) {
        List<Token> list = new ArrayList<>();

        boolean escaped = false;
        for (char c : string.toCharArray()) {
            if (escaped) {
                Token token = escapeAction.getOrDefault(c, () -> {
                    throw new IllegalArgumentException("The character is " + c + " is not supported after a \\." +
                            " Supported are: " + escapeAction.keySet()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(";")));
                }).get();
                list.add(token);
                escaped = false;
            } else {
                if (c == '\\') {
                    escaped = true;
                } else {
                    list.add(new CharToken(c));
                }
            }
        }
        if (escaped) {
            throw new IllegalArgumentException("The escape did not finish properly");
        }
        return list;
    }
}
