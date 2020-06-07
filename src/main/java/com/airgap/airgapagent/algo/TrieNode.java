package com.airgap.airgapagent.algo;


import java.io.Serializable;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class TrieNode implements Serializable {
    private final TrieNode[] children = new TrieNode[0x100];

    private TrieNode fail = null;
    private int[] patterns = new int[0];

    void setChild(char character, TrieNode child) {
        children[character] = child;
    }

    TrieNode getChild(char character) {
        return children[character];
    }

    public TrieNode getFail() {
        return fail;
    }

    public void setFail(TrieNode fail) {
        this.fail = fail;
    }

    public void setPattern(Integer patternIndex) {
        setPattern(new int[]{patternIndex});
    }

    public int[] getPatterns() {
        return patterns;
    }

    public void setPattern(int[] failTargets) {
        patterns = failTargets;
    }

    public void clearPattern() {
        patterns = new int[0];
    }
}
