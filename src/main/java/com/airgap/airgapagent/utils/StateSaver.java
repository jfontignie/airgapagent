package com.airgap.airgapagent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 11/14/2021.
 */
public class StateSaver {


    public static final String NULL_VALUE = "NULL";
    private static final Logger log = LoggerFactory.getLogger(StateSaver.class);

    private StateSaver() {
        //Nothing to do
    }

    private static void saveProperties(File file, Properties p) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            p.store(writer, "");
        }
    }

    private static Properties loadProperties(File file) throws IOException {
        Properties p = new Properties();
        if (file.exists() && file.isFile()) {
            try (FileReader reader = new FileReader(file)) {
                p.load(reader);
            }
        }
        return p;
    }

    public static void set(File file, String key, String value) throws IOException {
        Properties p = loadProperties(file);
        if (value == null) {
            value = NULL_VALUE;
        }
        p.setProperty(key, value);
        saveProperties(file, p);
    }


    public static String get(File file, String key) {
        Properties p;
        try {
            p = loadProperties(file);
        } catch (IOException e) {
            log.error("Impossible to load the properties, returning null", e);
            return null;
        }
        String value = p.getProperty(key);
        return NULL_VALUE.equals(value) ? null : value;
    }

}
