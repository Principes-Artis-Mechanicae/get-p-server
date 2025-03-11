package es.princip.getp.domain.auth;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(exclude = "expiration")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerification {

    private String email;
    private String verificationCode;
    private Long expiration;
    private LocalDateTime createdAt;

    public EmailVerification(
        final String email,
        final String verificationCode,
        final Long expiration
    ) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.expiration = expiration;
        this.createdAt = LocalDateTime.now();
    }

    public EmailVerification(
        final String email,
        final String verificationCode,
        final Long expiration,
        final LocalDateTime createdAt
    ) {
        this(email, verificationCode, expiration);
        this.createdAt = createdAt;
    }

    public boolean verify(String verificationCode) {
        return this.verificationCode.equals(verificationCode);
    }
}
