package com.airgap.airgapagent.algo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/9/2020.
 */
public enum SymbolType {

    LETTER(
            IntStream.concat(IntStream.rangeClosed('a', 'z'), IntStream.rangeClosed('A', 'Z'))
    ),
    DIGIT(IntStream.rangeClosed('0', '9')),

    ANY(IntStream.rangeClosed(0, 0xFF));


    private final List<Character> list;

    SymbolType(IntStream intStream) {
        this.list = intStream.boxed()
                .map(i -> (char) (int) i)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Character> listSymbols() {
        return list;
    }
}
