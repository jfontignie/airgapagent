package com.airgap.airgapagent.service;

import java.io.Reader;
import java.util.List;
import java.util.Optional;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
public interface CrawlService<T> {


    Optional<Reader> getContentReader(T t);

    boolean isLeaf(T current);

    List<T> listChildren(T current);
}
