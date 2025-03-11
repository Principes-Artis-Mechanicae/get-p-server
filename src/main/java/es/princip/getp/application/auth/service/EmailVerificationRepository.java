package es.princip.getp.application.auth.service;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface EmailVerificationRepository extends KeyValueRepository<EmailVerification, String> {

}