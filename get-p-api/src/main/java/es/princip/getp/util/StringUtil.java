package es.princip.getp.util;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class StringUtil {

    public static String camelToSnake(final String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        return camelCase.replaceAll(regex, replacement);
    }

    public static String generateRandomFilename(final String filename) {
        final String extension = StringUtils.getFilenameExtension(filename);
        final String randomFilename = UUID.randomUUID().toString();
        return String.format("%s.%s", randomFilename, extension);
    }
}