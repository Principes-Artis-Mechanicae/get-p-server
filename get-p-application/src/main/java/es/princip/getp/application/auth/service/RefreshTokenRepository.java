package es.princip.getp.application.auth.service;

import es.princip.getp.domain.auth.RefreshToken;

public interface RefreshTokenRepository {

    boolean existsByRefreshToken(String refreshToken);
    void save(RefreshToken refreshToken);
}