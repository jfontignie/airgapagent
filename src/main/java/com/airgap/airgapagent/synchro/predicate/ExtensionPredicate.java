package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.apache.commons.compress.utils.FileNameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
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
        if (ext.startsWith(".")) {
            ext = ext.substring(1);
        }
        extensions.add(ext);
    }

    @Override
    public void init() {
        //Nothing to do
    }

    @Override
    public boolean call(PathInfo path) {
        Path originalPath = path.getOriginalPath();
        if (Files.isDirectory(originalPath)) {
            return true;
        }
        String foundExtension = FileNameUtils.getExtension(originalPath.toString());
        return extensions.contains(foundExtension.toLowerCase());
    }

    @Override
    public void close() {
        //Nothing to do
    }
}
