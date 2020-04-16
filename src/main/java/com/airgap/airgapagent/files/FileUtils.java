package com.airgap.airgapagent.files;


import com.airgap.airgapagent.utils.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/13/2020.
 */
public class FileUtils {

    private FileUtils() {
        //Nothing to do
    }

    public static String buildTimestamp() {
        DateFormat format = new SimpleDateFormat("_yyyyMMdd_HHmmssSSSS");
        return format.format(new Date());
    }

    public static Path buildNewUniquePath(Path targetFile) {
        Pair<String, String> pair = separateExtension(targetFile.toString());
        targetFile = Path.of(pair.getFirst() + buildTimestamp() + pair.getSecond());
        return targetFile;
    }

    public static Pair<String, String> separateExtension(String fileName) {
        int extPos = fileName.lastIndexOf('.');
        if (extPos == -1) {
            return new Pair<>(fileName, "");
        } else {
            return new Pair<>(fileName.substring(0, extPos), fileName.substring(extPos));
        }
    }

    public static String calculateSignature(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest.getInstance("SHA-256");
        byte[] data = Files.readAllBytes(path);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        return Base64.getEncoder().encodeToString(hash);
    }
}
