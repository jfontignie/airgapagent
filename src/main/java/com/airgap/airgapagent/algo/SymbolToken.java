package com.airgap.airgapagent.algo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/10/2020.
 */
public class SymbolToken extends Token {


    private final SymbolType symbolType;

    public SymbolToken(SymbolType symbolType) {
        super(false);
        this.symbolType = symbolType;
    }

    public static List<Token> toList(SymbolType... symbols) {
        return Arrays.stream(symbols).map(SymbolToken::new).collect(Collectors.toList());
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    @Override
    public List<Character> matches() {
        return symbolType.listSymbols();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SymbolToken)) return false;
        SymbolToken that = (SymbolToken) o;
        return symbolType == that.symbolType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbolType);
    }
}
