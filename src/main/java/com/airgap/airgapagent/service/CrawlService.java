package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.DataReader;

import java.io.IOException;
import java.util.List;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/31/2020.
 */
public interface CrawlService<T> {


    DataReader<T> getContentReader(T t) throws IOException;

    boolean isLeaf(T current);

    List<T> listChildren(T current);
}
