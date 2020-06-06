package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.DataReader;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
@Service
public class ContentReaderService {

    private final Tika tika = new Tika();

    public DataReader<File> getContent(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        Metadata metadata = new Metadata();
        Reader reader = tika.parse(fis, metadata);
        Map<String, String> map = new HashMap<>();
        for (String name : metadata.names()) {
            map.put(name, metadata.get(name));
        }
        return new DataReader<>(file, map, reader);
    }

}
