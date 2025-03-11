package es.princip.getp.persistence.adapter.auth;

import es.princip.getp.domain.auth.EmailVerification;
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
public class EmailVerificationRedisEntity {

    @Id
    private String email;

    private String verificationCode;

    private LocalDateTime createdAt;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public static EmailVerificationRedisEntity from(final EmailVerification verification) {
        final EmailVerificationRedisEntity entity = new EmailVerificationRedisEntity();
        entity.email = verification.getEmail();
        entity.verificationCode = verification.getVerificationCode();
        entity.createdAt = verification.getCreatedAt();
        entity.expiration = verification.getExpiration();
        return entity;
    }
}
