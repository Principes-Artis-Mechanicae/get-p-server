package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.member.model.Email;

public interface VerificationCodeSender {
    void send(Email email, String verificationCode);
}