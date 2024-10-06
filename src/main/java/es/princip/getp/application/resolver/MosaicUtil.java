package es.princip.getp.application.resolver;

public class MosaicUtil {
    
    public static String convertMosaicMessage(String message, int length) {
        return message.repeat(Math.max(0, length));
    }

    public static int getLength(String value) {
        if (value != null) {
            return value.length();
        }
        return 0;
    }
}
