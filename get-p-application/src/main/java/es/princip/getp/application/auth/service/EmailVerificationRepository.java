package es.princip.getp.application.auth.service;

import es.princip.getp.domain.auth.EmailVerification;

public interface EmailVerificationRepository {

    void deleteById(String email);
    void save(EmailVerification verification);
    EmailVerification findById(String email);
}