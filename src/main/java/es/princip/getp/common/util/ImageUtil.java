package es.princip.getp.common.util;

import org.springframework.util.StringUtils;

public class ImageUtil {

    public static String generateRandomFilename(String filename) {
        String extension = StringUtils.getFilenameExtension(filename);
        String randomFilename = RandomUtil.generateRandomUUIDString();
        return String.format("%s.%s", randomFilename, extension);
    }
}
