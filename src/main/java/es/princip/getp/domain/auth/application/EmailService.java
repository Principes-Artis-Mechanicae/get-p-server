package es.princip.getp.domain.auth.application;

public interface EmailService {
    void sendEmail(String email, String title, String text);
}