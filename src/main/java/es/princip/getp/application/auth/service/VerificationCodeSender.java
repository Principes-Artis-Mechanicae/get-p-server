package es.princip.getp.application.auth.service;

import es.princip.getp.domain.common.model.Email;

public interface VerificationCodeSender {

    void send(Email email, String verificationCode);
}