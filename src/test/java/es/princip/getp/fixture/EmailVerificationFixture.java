package es.princip.getp.fixture;

import es.princip.getp.domain.auth.entity.EmailVerification;

public class EmailVerificationFixture {
    public static EmailVerification createEmailVerification(String email, String verificationCode) {
        return new EmailVerification(email, verificationCode);
    }
}
