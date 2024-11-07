package es.princip.getp.persistence.adapter.auth;

import es.princip.getp.application.auth.exception.NotFoundVerificationException;
import es.princip.getp.application.auth.service.EmailVerificationRepository;
import es.princip.getp.domain.auth.EmailVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class EmailVerificationRedisRepository implements EmailVerificationRepository {

    private final EmailVerificationKeyValueRepository repository;

    @Override
    public void deleteById(final String email) {
        repository.deleteById(email);
    }

    @Override
    public void save(final EmailVerification verification) {
        final EmailVerificationRedisEntity entity = EmailVerificationRedisEntity.from(verification);
        repository.save(entity);
    }

    @Override
    public EmailVerification findById(String email) {
        return repository.findById(email)
            .map(entity -> new EmailVerification(
                entity.getEmail(),
                entity.getVerificationCode(),
                entity.getExpiration(),
                entity.getCreatedAt()
            ))
            .orElseThrow(NotFoundVerificationException::new);
    }
}