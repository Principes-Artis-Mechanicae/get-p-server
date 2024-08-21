package es.princip.getp.application.auth.service;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "email_verification")
@EqualsAndHashCode(exclude = "expiration")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerification {

    @Id
    private String email;

    private String verificationCode;

    private LocalDateTime createdAt;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public EmailVerification(String email, String verificationCode, Long expiration) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.createdAt = LocalDateTime.now();
        this.expiration = expiration;
    }

    public boolean verify(String verificationCode) {
        return this.verificationCode.equals(verificationCode);
    }
}
