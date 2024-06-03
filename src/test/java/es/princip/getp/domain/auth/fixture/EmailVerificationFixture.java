package es.princip.getp.domain.auth.fixture;

import es.princip.getp.domain.auth.domain.entity.EmailVerification;

public class EmailVerificationFixture {
    public static EmailVerification createEmailVerification(String email, String verificationCode) {
        return new EmailVerification(email, verificationCode, 1000L);
    }
}
