package es.princip.getp.domain.auth.service;

public interface EmailService {
    public void sendEmail(String email, String title, String text);
}