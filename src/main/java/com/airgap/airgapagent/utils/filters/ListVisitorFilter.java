package com.airgap.airgapagent.utils.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * com.airgap.airgapagent.utils.filters
 * Created by Jacques Fontignie on 11/2/2021.
 */
public class ListVisitorFilter<T> implements VisitorFilter<T> {

    private final List<VisitorFilter<T>> list;

    public ListVisitorFilter(List<VisitorFilter<T>> list) {
        this.list = list;
    }

    public ListVisitorFilter() {
        this(new ArrayList<>());
    }

    public void addFilter(VisitorFilter<T> filter) {
        list.add(filter);
    }

    @Override
    public boolean accept(T t) {
        for (VisitorFilter<T> filter : list) {
            if (!filter.accept(t)) {
                return false;
            }
        }
        return true;
    }
}
