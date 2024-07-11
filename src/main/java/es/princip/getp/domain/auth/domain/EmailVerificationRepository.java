package es.princip.getp.domain.auth.domain;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface EmailVerificationRepository extends KeyValueRepository<EmailVerification, String> {

}