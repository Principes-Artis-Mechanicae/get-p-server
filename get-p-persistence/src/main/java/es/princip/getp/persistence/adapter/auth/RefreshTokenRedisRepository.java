package es.princip.getp.persistence.adapter.auth;

import es.princip.getp.application.auth.service.RefreshTokenRepository;
import es.princip.getp.domain.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class RefreshTokenRedisRepository implements RefreshTokenRepository {

    private final RefreshTokenKeyValueRepository repository;

    @Override
    public boolean existsByRefreshToken(final String refreshToken) {
        return repository.existsByRefreshToken(refreshToken);
    }

    @Override
    public void save(final RefreshToken refreshToken) {
        final RefreshTokenRedisEntity entity = RefreshTokenRedisEntity.from(refreshToken);
        repository.save(entity);
    }
}