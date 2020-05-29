package com.airgap.airgapagent.algo;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class TrieNode {

    private final TrieNode[] children = new TrieNode[0x100];

    void setChild(final char character, final TrieNode child) {
        children[character] = child;
    }

    TrieNode getChild(final char character) {
        return children[character];
    }

}
