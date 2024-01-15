package es.princip.getp.global.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtil {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_+=/])[A-Za-z\\d!@#$%^&*_+=/]{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    public static boolean isValidPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
