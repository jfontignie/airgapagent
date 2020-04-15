package com.airgap.airgapagent.files;


import com.airgap.airgapagent.utils.Pair;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.airgap.airgapagent.files
 * Created by Jacques Fontignie on 4/13/2020.
 */
public class FileUtils {

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
        int extPos = fileName.lastIndexOf(".");
        if (extPos == -1) {
            return new Pair<>(fileName, "");
        } else {
            return new Pair<>(fileName.substring(0, extPos), fileName.substring(extPos));
        }
    }
}