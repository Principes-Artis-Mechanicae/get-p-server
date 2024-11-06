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
    private LocalDateTime createdAt;
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
