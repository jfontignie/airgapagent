package com.airgap.airgapagent.synchro;

import org.apache.commons.compress.utils.FileNameUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/19/2020.
 */
public class ExtensionPredicate implements Predicate {


    private final Set<String> extensions = new HashSet<>();

    public Set<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(Set<String> extensions) {
        this.extensions.clear();
        extensions.forEach(this::addExtension);

    }

    private void addExtension(String extension) {
        String ext = extension.toLowerCase();
        if (!extension.startsWith(".")) {
            ext = "." + extension;
        }
        extensions.add(ext);
    }

    @Override
    public void init() {
        //Nothing to do
    }

    @Override
    public boolean call(PathInfo path) {
        return extensions.contains(FileNameUtils.getExtension(path.toString()).toLowerCase());
    }

    @Override
    public void close() {
        //Nothing to do
    }
}
