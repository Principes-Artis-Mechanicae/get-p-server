package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.member.command.domain.model.Email;

public interface VerificationCodeSender {
    void send(Email email, String text);
}