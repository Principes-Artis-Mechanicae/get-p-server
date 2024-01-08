package es.princip.getp.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import es.princip.getp.domain.auth.entity.EmailVerification;

public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}