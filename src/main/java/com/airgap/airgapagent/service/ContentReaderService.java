package com.airgap.airgapagent.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
@Service
public class ContentReaderService {

    private final Tika tika = new Tika();

    public Optional<Reader> getContent(File file) {
        try {
            return Optional.of(tika.parse(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
