package com.airgap.airgapagent.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * com.airgap.airgapagent.service
 * Created by Jacques Fontignie on 5/29/2020.
 */
@Service
public class CorpusBuilderService {

    private static final Logger log = LoggerFactory.getLogger(CorpusBuilderService.class);

    public Set<String> buildSet(File file) throws IOException {
        return build(file, new HashSet<>());
    }

    private Set<String> build(File file, Set<String> set) throws IOException {
        log.info("Reading list of exact matches in {}", file);
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
        MappingIterator<String[]> iterator = mapper.readerFor(String[].class).with(bootstrapSchema).readValues(file);

        while (iterator.hasNext()) {
            set.add(iterator.next()[0]);
        }
        log.info("Found {} corpus in file", set.size());
        return set;
    }


}
