package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.DataReader;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
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

    private static final Logger log = LoggerFactory.getLogger(ContentReaderService.class);

    private static final String CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<properties>\n" +
            "    <parsers>\n" +
            "        <parser class=\"org.apache.tika.parser.DefaultParser\">\n" +
            "       <parser-exclude class=\"org.apache.tika.parser.ocr.TesseractOCRParser\"/>\n" +
            "        </parser>\n" +
            "    </parsers>\n" +
            "</properties>";

    public DataReader<File> getContent(File file) throws IOException {
        Metadata metadata = new Metadata();
        TikaConfig config = buildConfig();
        Tika tika = (config == null) ? new Tika() : new Tika(config);
        Reader reader = tika.parse(TikaInputStream.get(file.toPath()), metadata);
        Map<String, String> map = new HashMap<>();
        for (String name : metadata.names()) {
            map.put(name, metadata.get(name));
        }
        return new DataReader<>(file, map, reader);
    }

    private TikaConfig buildConfig() throws IOException {
        TikaConfig config = null;
        try {
            config = new TikaConfig(new ByteArrayInputStream(CONFIG.getBytes()));
        } catch (TikaException | SAXException e) {
            log.error("Impossible to build config, creating temporary config", e);
        }
        return config;
    }

}
