package com.airgap.airgapagent.utils;

import java.io.File;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class FileStateConverter implements StateConverter<File> {

    private static final FileStateConverter INSTANCE = new FileStateConverter();

    private FileStateConverter() {

    }

    public static FileStateConverter of() {
        return INSTANCE;
    }

    @Override
    public String persist(File t) {
        return t.toString();
    }

    @Override
    public File load(String s) {
        return new File(s);
    }
}
