package es.princip.getp.domain.auth.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "email_verification")
public class EmailVerification {

    @Id
    private String email;

    private String verificationCode;

    private LocalDateTime createdAt;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiration;

    public EmailVerification(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.createdAt = LocalDateTime.now();
        this.expiration = 5L;
    }

    public boolean verify(String verificationCode) {
        return this.verificationCode.equals(verificationCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailVerification that)) {
            return false;
        }
        return Objects.equals(email, that.email) &&
            Objects.equals(verificationCode,that.verificationCode) &&
            Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, verificationCode, createdAt);
    }
}
