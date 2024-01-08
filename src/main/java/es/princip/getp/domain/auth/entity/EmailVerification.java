package es.princip.getp.domain.auth.entity;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import lombok.Getter;

@Getter
@RedisHash(value = "email_verification")
public class EmailVerification {
    @Id
    private String email;

    private String verificationCode;

    private boolean isVerified;

    private LocalDateTime createdAt;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiration;

    public EmailVerification(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.isVerified = false;
        this.createdAt = LocalDateTime.now();
        this.expiration = 5L;
    }

    public boolean verify(String verificationCode) {
        if (this.verificationCode.equals(verificationCode)) {
            this.isVerified = true;
            this.expiration = 30L;
            return true;
        }
        return false;
    }
}
