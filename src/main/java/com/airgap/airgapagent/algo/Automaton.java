package com.airgap.airgapagent.algo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class Automaton {
    private final char[][] originalPatterns;
    private final Map<TrieNode, TrieNode> fail = new HashMap<>();
    private final Map<TrieNode, int[]> patterns = new HashMap<>();
    private final boolean caseSensitive;
    private TrieNode root;


    public Automaton(boolean caseSensitive, char[]... patterns) {
        this(caseSensitive, Arrays.stream(patterns)
                .map(String::valueOf)
                .collect(Collectors.toSet()));
    }

    public Automaton(boolean caseSensitive, Set<String> patterns) {
        originalPatterns = patterns.stream()
                .map(s -> caseSensitive ? s : s.toLowerCase())
                .map(String::toCharArray)
                .collect(Collectors.toList())
                .toArray(new char[0][0]);
        this.caseSensitive = caseSensitive;
        constructTrie(originalPatterns);
        computeFailureFunction();
    }

    public Automaton(char[][] patterns) {
        this(true, patterns);
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    private static char toUnsignedchar(final int value) {
        return (char) (0xFF & value);
    }

    private void constructTrie(final char[]... patterns) {
        root = new TrieNode();
        final int k = patterns.length;

        for (int patternIndex = 0; patternIndex < k; ++patternIndex) {
            TrieNode node = root;
            int charIndex = 0;
            final int patternLength = patterns[patternIndex].length;

            while (charIndex < patternLength && node.getChild(patterns[patternIndex][charIndex]) != null) {
                node = node.getChild(patterns[patternIndex][charIndex]);
                ++charIndex;
            }

            while (charIndex < patternLength) {
                final TrieNode u = new TrieNode();
                node.setChild(patterns[patternIndex][charIndex], u);
                node = u;
                ++charIndex;
            }

            this.patterns.put(node, new int[]{patternIndex});
        }

        this.patterns.put(root, new int[0]);

    }

    @SuppressWarnings("java:S3776")
    private void computeFailureFunction() {
        final TrieNode fallbackNode = new TrieNode();

        for (int c = 0; c < 0x100; ++c) {
            final char b = toUnsignedchar(c);
            fallbackNode.setChild(b, root);
        }

        fail.put(root, fallbackNode);
        final Deque<TrieNode> queue = new ArrayDeque<>();
        queue.addLast(root);

        while (!queue.isEmpty()) {
            final TrieNode head = queue.removeFirst();

            for (int c = 0; c < 0x100; ++c) {
                final char character = toUnsignedchar(c);

                if (head.getChild(character) != null) {

                    final TrieNode child = head.getChild(character);
                    TrieNode w = fail.get(head);

                    while (w.getChild(character) == null) {
                        w = fail.get(w);
                    }

                    fail.put(child, w.getChild(character));

                    final int[] failTargets = patterns.get(fail.get(child));

                    final int[] existingList = patterns.get(child);
                    if (existingList == null) {
                        patterns.put(child, failTargets);
                    } else {
                        final int[] extendedList = Arrays.copyOf(existingList, existingList.length + failTargets.length);
                        System.arraycopy(failTargets, 0, extendedList, existingList.length, failTargets.length);
                        patterns.put(child, extendedList);
                    }
                    queue.addLast(child);
                }
            }
        }

        patterns.put(root, new int[0]);
    }

    public TrieNode getRoot() {
        return root;
    }

    public Map<TrieNode, TrieNode> getFail() {
        return fail;
    }

    public Map<TrieNode, int[]> getPatterns() {
        return patterns;
    }

    public char[] getPattern(int patternIndex) {
        return originalPatterns[patternIndex];
    }
}
