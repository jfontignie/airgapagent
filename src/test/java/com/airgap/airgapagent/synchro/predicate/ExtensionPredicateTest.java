package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 4/26/2020.
 */
class ExtensionPredicateTest {

    private final ExtensionPredicate extensionPredicate = new ExtensionPredicate();

    @BeforeEach()
    public void setUp() {
        extensionPredicate.init();
    }

    @AfterEach
    public void tearDown() {
        extensionPredicate.close();
    }


    @Test
    public void testMultiple() {
        extensionPredicate.setExtensions(Set.of(".txt", ".TXT", "txt", ".TxT"));
        Assertions.assertEquals(1, extensionPredicate.getExtensions().size());
    }

    @Test
    void callDirectory() {
        Assertions.assertTrue(extensionPredicate.call(PathInfo.of("src/test/resources")));
    }


    @Test
    void callSelectedExtension() {
        ExtensionPredicate predicate = new ExtensionPredicate();
        predicate.setExtensions(Set.of("txt"));
        Assertions.assertTrue(predicate.call(PathInfo.of("src/test/resources/sample/sample.txt")));
    }

    @Test
    void callNonSelectedExtension() {
        ExtensionPredicate predicate = new ExtensionPredicate();
        Assertions.assertFalse(predicate.call(PathInfo.of("src/test/resources/sample/sample.txt")));
    }
}
