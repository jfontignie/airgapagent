package com.airgap.airgapagent.service;

import com.airgap.airgapagent.utils.DataReader;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    private final Tika tika = new Tika();

    public DataReader<File> getContent(File file) throws IOException {
        Metadata metadata = new Metadata();
        Reader reader = tika.parse(TikaInputStream.get(file.toPath()), metadata);
        Map<String, String> map = new HashMap<>();
        for (String name : metadata.names()) {
            map.put(name, metadata.get(name));
        }
        return new DataReader<>(file, map, reader);
    }

    public DataReader<MimeMessage> getContent(MimeMessage message) throws MessagingException, IOException {
        Metadata metadata = new Metadata();
        Reader reader = tika.parse(message.getInputStream(), metadata);
        Map<String, String> map = new HashMap<>();
        for (String name : metadata.names()) {
            map.put(name, metadata.get(name));
        }
        return new DataReader<>(message, map, reader);
    }

}
