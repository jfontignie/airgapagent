package com.airgap.airgapagent.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.StringJoiner;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/15/2020.
 */
public class FileMetadata extends Metadata {
    private static final Logger logger = LoggerFactory.getLogger(FileMetadata.class);
    private final String signature;
    private final String contentType;
    private final long size;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    FileMetadata(@JsonProperty("fileTime") long fileTime,
                 @JsonProperty("path") Path path,
                 @JsonProperty("type") Type type,
                 @JsonProperty("fileName") String fileName,
                 @JsonProperty("signature") String signature,
                 @JsonProperty("contenType") String contentType,
                 @JsonProperty("size") long size) {
        super(fileTime, path, type, fileName);
        this.signature = signature;
        this.contentType = contentType;
        this.size = size;
    }

    public FileMetadata(Path path) throws IOException {
        super(path);
        String signature1;
        try {
            signature1 = calculateSignature(path);
        } catch (NoSuchAlgorithmException e) {
            signature1 = "";
            logger.info("Impossible to calculate signature", e);
        }

        signature = signature1;
        contentType = Files.probeContentType(path);
        size = Files.size(path);
    }

    private String calculateSignature(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest.getInstance("SHA-256");
        byte[] data = Files.readAllBytes(path);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        return Base64.getEncoder().encodeToString(hash);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", FileMetadata.class.getSimpleName() + "[", "]")
                .add("parent='" + super.toString() + "'")
                .add("signature='" + signature + "'")
                .add("contentType='" + contentType + "'")
                .toString();
    }
}
