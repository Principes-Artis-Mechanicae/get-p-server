package es.princip.getp.global.util;

import org.apache.tika.Tika;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class ImageUtil {
    private static final Tika tika = new Tika();

    private static final List<String> whiteImageExtensionList = Arrays.asList(
        "image/jpeg",
        "image/pjpeg",
        "image/png",
        "image/gif",
        "image/bmp",
        "image/x-windows-bmp"
    );

    public static boolean isValidImage(InputStream inputStream) {
        try {
            String mimeType = tika.detect(inputStream);
            return whiteImageExtensionList.stream()
                    .anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));
        } catch (IOException exception) {

            return false;
        }
    }

    public static String generateRandomFilename(String filename) {
        String extension = StringUtils.getFilenameExtension(filename);
        String randomFilename = RandomUtil.generateRandomUUIDString();
        return String.format("%s.%s", randomFilename, extension);
    }
}
