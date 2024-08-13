package es.princip.getp.infra.util;

import org.springframework.util.StringUtils;

public class ImageUtil {

    public static String generateRandomFilename(String filename) {
        String extension = StringUtils.getFilenameExtension(filename);
        String randomFilename = RandomUtil.generateRandomUUIDString();
        return String.format("%s.%s", randomFilename, extension);
    }
}
