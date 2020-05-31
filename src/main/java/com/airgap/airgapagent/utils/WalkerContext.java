package com.airgap.airgapagent.utils;

import java.io.File;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/24/2020.
 */
public class WalkerContext<T> {

    private final T root;
    private T reference;
    private int visited;

    public WalkerContext(T root) {
        this.root = root;
        visited = 0;
    }

    public static WalkerContext<File> of(String s) {
        return of(new File(s));
    }

    public static WalkerContext<File> of(File file) {
        return new WalkerContext<>(file);
    }

    public T getReference() {
        return reference;
    }

    public void setReference(T f) {
        this.reference = f;
    }

    public T getRoot() {
        return root;
    }

    public void reset() {
        this.reference = null;
    }

    public void incVisited() {
        visited++;
    }

    public int getVisited() {
        return visited;
    }
}
