package es.princip.getp.persistence.adapter.auth;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RefreshTokenKeyValueRepository extends KeyValueRepository<RefreshTokenRedisEntity, Long> {

    boolean existsByRefreshToken(String refreshToken);
}