package com.airgap.airgapagent.utils;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/24/2020.
 */
public class CrawlState<T> {

    private final T root;
    private T original;
    private T reference;
    private int visited;

    public CrawlState(T root) {
        this.root = root;
        visited = 0;
    }

    public static <T> CrawlState<T> of(T t) {
        return new CrawlState<>(t);
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

    public T getOriginal() {
        return original;
    }

    public T init() {
        this.original = reference;
        return getReference();
    }
}
