package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.DataReader;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public abstract class SearchEventAdapter<T> implements SearchEventListener<T> {
    @Override
    public void onInit() {
        //Nothing to do
    }

    @Override
    public void onFound(CrawlState<T> crawlState, ExactMatchResult<T> result) {
        //Nothing to do
    }

    @Override
    public void onError(DataReader<T> object, Throwable error) {
        //Nothing to do
    }

    @Override
    public void onClose(CrawlState<T> crawlState) {
        //Nothing to do
    }

    @Override
    public void onVisited(CrawlState<T> state, T object) {
        //Nothing to do
    }
}
