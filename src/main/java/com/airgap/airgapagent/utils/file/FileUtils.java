package com.airgap.airgapagent.utils.file;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/3/2020.
 */
public class FileUtils {

    private static final int MAX_BOUND = 100;
    private static final Random RANDOM = new Random();

    private FileUtils() {
        //Nothing to do
    }

    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return format.format(new Date());
    }

    public static Path withTimeStamp(Path targetPath) {
        String target = targetPath.toString();
        String path = FilenameUtils.getPath(target);
        String baseName = FilenameUtils.getBaseName(target);
        String extension = FilenameUtils.getExtension(target);
        return Path.of(path, baseName + "_" + getTimeStamp() + "." + extension);
    }

    @SuppressWarnings("java:S2245")
    public static Path withRandomTimeStamp(Path targetPath) {
        String target = targetPath.toString();
        String path = FilenameUtils.getPath(target);
        String baseName = FilenameUtils.getBaseName(target);
        String extension = FilenameUtils.getExtension(target);

        int randomValue = FileUtils.RANDOM.nextInt(MAX_BOUND);
        return Path.of(path, baseName + "_" + getTimeStamp() + "_" + randomValue + "." + extension);
    }
}
