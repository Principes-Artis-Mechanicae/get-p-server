package es.princip.getp.domain.auth.repository;

import es.princip.getp.domain.auth.domain.entity.EmailVerification;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface EmailVerificationRepository extends KeyValueRepository<EmailVerification, String> {

}