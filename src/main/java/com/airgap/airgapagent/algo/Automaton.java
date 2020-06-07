package com.airgap.airgapagent.algo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 5/29/2020.
 */
public class Automaton implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Automaton.class);

    private final char[][] originalPatterns;
    private final Set<AutomatonOption> options;
    private TrieNode root;


    public Automaton(Set<AutomatonOption> automatonOptions, char[]... patterns) {
        this(automatonOptions, Arrays.stream(patterns)
                .map(String::valueOf)
                .collect(Collectors.toSet()));
    }

    public Automaton(Set<AutomatonOption> automatonOptions, Set<String> patterns) {
        originalPatterns = patterns.stream()
                .map(s -> automatonOptions.contains(AutomatonOption.CASE_INSENSITIVE) ? s.toLowerCase() : s)
                .sorted()
                .map(String::toCharArray)
                .collect(Collectors.toList())
                .toArray(new char[0][0]);
        log.info("Automaton built with corpus size : {}", originalPatterns.length);
        this.options = automatonOptions;
        constructTrie(originalPatterns);
        computeFailureFunction();
    }

    public Automaton(char[][] patterns) {
        this(Set.of(AutomatonOption.CASE_INSENSITIVE), patterns);
    }

    public Set<AutomatonOption> getOptions() {
        return options;
    }

    private static char toUnsignedchar(final int value) {
        return (char) (0xFF & value);
    }

    private void constructTrie(final char[]... patterns) {
        root = new TrieNode();

        for (int patternIndex = 0; patternIndex < patterns.length; patternIndex++) {
            TrieNode node = root;
            int charIndex = 0;
            char[] currentPattern = patterns[patternIndex];
            final int patternLength = currentPattern.length;

            while (charIndex < patternLength && node.getChild(currentPattern[charIndex]) != null) {
                node = node.getChild(currentPattern[charIndex]);
                ++charIndex;
            }

            while (charIndex < patternLength) {
                final TrieNode u = new TrieNode();
                node.setChild(currentPattern[charIndex], u);
                node = u;
                ++charIndex;
            }

            node.setPattern(patternIndex);
        }

        root.clearPattern();

    }

    @SuppressWarnings("java:S3776")
    private void computeFailureFunction() {
        final TrieNode fallbackNode = new TrieNode();

        for (int c = 0; c < 0x100; ++c) {
            final char b = toUnsignedchar(c);
            fallbackNode.setChild(b, root);
        }

        root.setFail(fallbackNode);
        final Deque<TrieNode> queue = new ArrayDeque<>();
        queue.addLast(root);

        while (!queue.isEmpty()) {
            final TrieNode head = queue.removeFirst();

            for (int c = 0; c < 0x100; ++c) {
                final char character = toUnsignedchar(c);

                if (head.getChild(character) != null) {

                    final TrieNode child = head.getChild(character);
                    TrieNode w = head.getFail();

                    while (w.getChild(character) == null) {
                        w = w.getFail();
                    }

                    child.setFail(w.getChild(character));

                    final int[] failTargets = child.getFail().getPatterns();

                    final int[] existingList = child.getPatterns();
                    if (existingList == null) {
                        child.setPattern(failTargets);
                    } else {
                        final int[] extendedList = Arrays.copyOf(existingList, existingList.length + failTargets.length);
                        System.arraycopy(failTargets, 0, extendedList, existingList.length, failTargets.length);
                        child.setPattern(extendedList);
                    }
                    queue.addLast(child);
                }
            }
        }
        root.clearPattern();
    }

    public TrieNode getRoot() {
        return root;
    }

    public char[] getPattern(int patternIndex) {
        return originalPatterns[patternIndex];
    }
}
