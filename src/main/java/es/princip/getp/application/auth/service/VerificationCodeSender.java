package es.princip.getp.application.auth.service;

import es.princip.getp.domain.member.model.Email;

public interface VerificationCodeSender {

    void send(Email email, String verificationCode);
}