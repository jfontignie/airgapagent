package com.airgap.airgapagent.utils.visitor;

import java.io.Closeable;

/**
 * com.airgap.airgapagent.utils.visitor
 * Created by Jacques Fontignie on 11/2/2021.
 */
public interface StateVisitor extends Closeable {

    void init();

    void visit(int numVisited);
}
