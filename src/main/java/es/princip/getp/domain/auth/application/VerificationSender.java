package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.member.command.domain.model.Email;

public interface VerificationSender {
    void send(Email email, String text);
}