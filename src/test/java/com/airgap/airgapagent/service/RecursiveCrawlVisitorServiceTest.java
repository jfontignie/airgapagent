package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.CrawlState;
import com.airgap.airgapagent.utils.filters.AlwaysVisitorFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 6/1/2020.
 */
class RecursiveCrawlVisitorServiceTest {

    private RecursiveCrawlVisitorService recursiveCrawlVisitorService;
    private CrawlService<String> crawlService;
    private CrawlState<String> context;
    private String childB;
    private String childA;

    @BeforeEach
    void setUp() {
        recursiveCrawlVisitorService = new RecursiveCrawlVisitorService();
        crawlService = Mockito.mock(CrawlService.class);
        String root = "root";
        childA = "a";
        childB = "b";

        Mockito.doReturn(Arrays.asList(childA, childB)).when(crawlService).listChildren(root);
        Mockito.doReturn(true).when(crawlService).isLeaf(childA);
        Mockito.doReturn(true).when(crawlService).isLeaf(childB);

        context = new CrawlState<>(root);

    }

    @Test
    void resumeVisit() {
        context.setCurrent(childB);
        List<String> found = Objects.requireNonNullElse(
                recursiveCrawlVisitorService.list(new AlwaysVisitorFilter<>(),
                                crawlService, context)
                        .collect(Collectors.toList())
                        .block(),
                Collections.emptyList());
        Assertions.assertEquals(1, found.size());
        Assertions.assertTrue(found.contains(childB));
    }

    @Test
    void visit() {


        List<String> found = Objects.requireNonNullElse(
                recursiveCrawlVisitorService.list(new AlwaysVisitorFilter<>(),
                                crawlService, context)
                        .collect(Collectors.toList())
                        .block(),
                Collections.emptyList());
        Assertions.assertEquals(2, found.size());
        Assertions.assertTrue(found.contains(childA));
        Assertions.assertTrue(found.contains(childB));
    }

}
