package com.airgap.airgapagent.utils.file;

import com.airgap.airgapagent.utils.Serializer;

import java.io.File;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/31/2020.
 */
public class FileSerializer implements Serializer<File> {

    private static final FileSerializer INSTANCE = new FileSerializer();

    private FileSerializer() {
        //Nothing to do
    }

    public static FileSerializer of() {
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
