package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.member.domain.model.Email;

public interface EmailService {
    void sendEmail(Email email, String title, String text);
}