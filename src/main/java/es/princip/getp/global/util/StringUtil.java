package es.princip.getp.global.util;

public class StringUtil {

    public static String camelToSnake(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        return camelCase.replaceAll(regex, replacement);
    }
}