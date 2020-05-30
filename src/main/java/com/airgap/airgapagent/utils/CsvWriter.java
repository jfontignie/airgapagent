package com.airgap.airgapagent.utils;

import com.airgap.airgapagent.domain.ExactMatchingResult;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

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
                    .toString());
            writer.flush();
        }
    }

    public static CsvWriter of(File file) throws IOException {
        return new CsvWriter(file);
    }

    public void save(ExactMatchingResult result) {
        try {
            writer.write(
                    new StringJoiner(CSV_SEPARATOR, "", "\n")
                            .add(result.getSource())
                            .add(String.valueOf(result.getOccurrences())).toString());
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @PreDestroy
    public void close() throws IOException {
        writer.close();
    }

}
