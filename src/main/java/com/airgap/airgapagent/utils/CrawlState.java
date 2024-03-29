package com.airgap.airgapagent.utils;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/24/2020.
 */
public class CrawlState<T> {

    private final T root;
    private T original;
    private T current;

    private int crawled;
    private int visited;
    private int found;
    private int error;

    public CrawlState(T root) {
        this.root = root;
        crawled = 0;
        visited = 0;
        found = 0;
        error = 0;
    }

    public static <T> CrawlState<T> of(T t) {
        return new CrawlState<>(t);
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T f) {
        this.current = f;
    }

    public T getRoot() {
        return root;
    }

    public void reset() {
        this.current = null;
    }

    public void incVisited() {
        visited++;
    }

    public void incFound() {
        found++;
    }

    public void incError() {
        error++;
    }

    public void incCrawled() {
        crawled++;
    }

    public int getVisited() {
        return visited;
    }

    public T getOriginal() {
        return original;
    }

    public T init() {
        this.original = current;
        return getCurrent();
    }

    public int getFound() {
        return found;
    }

    public int getError() {
        return error;
    }

    public int getCrawled() {
        return crawled;
    }
}
