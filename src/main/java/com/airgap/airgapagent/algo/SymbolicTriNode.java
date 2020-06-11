package com.airgap.airgapagent.algo;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/9/2020.
 */
public class SymbolicTriNode extends TrieNode {
    private final SymbolType symbol;

    public SymbolicTriNode(SymbolType symbol) {
        super();
        this.symbol = symbol;
    }

    public SymbolType getSymbol() {
        return symbol;
    }
}
