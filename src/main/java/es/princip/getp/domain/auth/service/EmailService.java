package es.princip.getp.domain.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendEmail(String email, String title, String text);
}