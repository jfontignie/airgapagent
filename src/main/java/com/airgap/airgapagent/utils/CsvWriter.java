package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.domain.ExactMatchingResult;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class CsvWriter implements Closeable {

    private static final String CSV_SEPARATOR = ";";
    private final FileWriter writer;


    public CsvWriter(File fileLocation) throws IOException {
        boolean needsHeader = !fileLocation.exists();
        writer = new FileWriter(fileLocation, true);
        if (needsHeader) {
            writer.write(new StringJoiner(CSV_SEPARATOR, "", "\n")
                    .add("File")
                    .add("Occurrences")
                    .add("Metadatas")
                    .toString());
            writer.flush();
        }
    }

    public static CsvWriter of(File file) throws IOException {
        return new CsvWriter(file);
    }

    public <T> void save(ExactMatchingResult<T> result, StateConverter<T> converter) {
        try {
            writer.write(
                    new StringJoiner(CSV_SEPARATOR, "", "\n")
                            .add(converter.persist(result.getDataSource().getSource()))
                            .add(String.valueOf(result.getOccurrences()))
                            .add(convert(result.getDataSource().getMetadata()))
                            .toString());
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String convert(Map<String, String> metadata) {
        return metadata.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(CSV_SEPARATOR));

    }

    @PreDestroy
    public void close() throws IOException {
        writer.close();
    }

}
