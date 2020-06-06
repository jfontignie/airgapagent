package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.DataReader;

import java.util.List;
import java.util.Optional;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
public interface CrawlService<T> {


    Optional<DataReader<T>> getContentReader(T t);

    boolean isLeaf(T current);

    List<T> listChildren(T current);
}
