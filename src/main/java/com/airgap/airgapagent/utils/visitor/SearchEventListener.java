package com.airgap.airgapagent.utils.visitor;

import com.airgap.airgapagent.domain.ExactMatchResult;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.DataReader;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/3/2021.
 */
public interface SearchEventListener<T> {

    void onInit();

    void onBefore(CrawlState<T> crawlState, T object);

    void onFound(CrawlState<T> crawlState, ExactMatchResult<T> result);

    void onError(DataReader<T> object, Throwable error);

    void onClose(CrawlState<T> crawlState);
}
