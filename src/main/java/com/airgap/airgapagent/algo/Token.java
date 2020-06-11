package com.airgap.airgapagent.algo;

import java.util.List;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/10/2020.
 */
public abstract class Token {

    private final boolean isChar;

    public Token(boolean isChar) {
        this.isChar = isChar;
    }

    public boolean isChar() {
        return isChar;
    }

    public abstract List<Character> matches();
}
