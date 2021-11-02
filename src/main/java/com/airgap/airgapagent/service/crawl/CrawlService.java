package com.airgap.airgapagent.service.crawl;

import com.airgap.airgapagent.utils.DataReader;

import java.util.List;
import java.util.Optional;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
public interface CrawlService<T> {

    /**
     * @return Given an object, returns its datareader
     */
    Optional<DataReader<T>> getContentReader(T t);

    /**
     * @param current the current object
     * @return if the object is a leaf or if there are children
     */
    boolean isLeaf(T current);

    /**
     * @param current the object to scan
     * @return the list of childrent of the object current
     */
    List<T> listChildren(T current);
}
