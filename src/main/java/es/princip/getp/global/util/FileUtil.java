package es.princip.getp.global.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.tika.Tika;
import org.springframework.util.StringUtils;

public class FileUtil {
    private static final Tika tika = new Tika();
    private static final List<String> whiteImageExtensionList = Arrays.asList("image/jpeg",
            "image/pjpeg", "image/png", "image/gif", "image/bmp", "image/x-windows-bmp");

    public static boolean isValidImageFile(InputStream inputStream) {
        try {
            String mimeType = tika.detect(inputStream);
            boolean isValidImage = whiteImageExtensionList.stream()
                    .anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));
            return isValidImage;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String convertToRandomFilename(String filename) {
        String extension = StringUtils.getFilenameExtension(filename);
        String randomFilename = RandomUtil.generateRandomUUIDString();
        return String.format("%s.%s", randomFilename, extension);
    }
}
