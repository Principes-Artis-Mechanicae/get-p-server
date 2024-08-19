package es.princip.getp.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class RandomUtil {
    public static String generateRandomUUIDString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String generateRandomCode(int length) {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            log.debug("RandomUtil.generateRandomCode() 예외 발생");
            throw new RuntimeException();
        }
    }
}
