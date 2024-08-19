package es.princip.getp.domain.auth.fixture;

import es.princip.getp.domain.auth.domain.EmailVerification;
import es.princip.getp.domain.member.model.Email;

public class EmailVerificationFixture {
    public static String VERIFICATION_CODE = "1234";

    public static EmailVerification emailVerification(Email email, String verificationCode) {
        return new EmailVerification(email.getValue(), verificationCode, 1000L);
    }
}
