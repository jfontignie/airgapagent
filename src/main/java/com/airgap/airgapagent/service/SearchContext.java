package com.airgap.airgapagent.service;

import com.airgap.airgapagent.algo.SearchAlgorithm;
import com.airgap.airgapagent.configuration.AbstractScanConfiguration;
import com.airgap.airgapagent.service.crawl.CrawlService;
import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.filters.VisitorFilter;
import com.airgap.airgapagent.utils.visitor.SearchEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 11/3/2021.
 */
public class SearchContext<T> {


    private final VisitorFilter<T> visitorFilter;
    private final CrawlService<T> crawlService;
    private final SearchAlgorithm searchAlgorithm;
    private final AbstractScanConfiguration<T> configuration;
    private final CrawlState<T> crawlState;
    private final List<SearchEventListener<T>> listeners;

    public SearchContext(VisitorFilter<T> visitorFilter, CrawlService<T> crawlService, SearchAlgorithm searchAlgorithm, AbstractScanConfiguration<T> configuration, CrawlState<T> crawlState) {
        this.visitorFilter = visitorFilter;
        this.crawlService = crawlService;
        this.searchAlgorithm = searchAlgorithm;
        this.configuration = configuration;
        this.crawlState = crawlState;
        this.listeners = new ArrayList<>();
    }

    public void addListener(SearchEventListener<T> listener) {
        listeners.add(listener);
    }

    public VisitorFilter<T> getVisitorFilter() {
        return visitorFilter;
    }

    public CrawlService<T> getCrawlService() {
        return crawlService;
    }

    public SearchAlgorithm getSearchAlgorithm() {
        return searchAlgorithm;
    }

    public AbstractScanConfiguration<T> getConfiguration() {
        return configuration;
    }

    public CrawlState<T> getCrawlState() {
        return crawlState;
    }

    public List<SearchEventListener<T>> getListeners() {
        return listeners;
    }
}
