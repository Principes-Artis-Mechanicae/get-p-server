package es.princip.getp.fixture.auth;

import es.princip.getp.application.auth.service.EmailVerification;
import es.princip.getp.domain.common.model.Email;

public class EmailVerificationFixture {

    public static String VERIFICATION_CODE = "1234";

    public static EmailVerification emailVerification(Email email, String verificationCode) {
        return new EmailVerification(email.getValue(), verificationCode, 1000L);
    }
}
