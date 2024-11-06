package es.princip.getp.persistence.adapter.auth;

import es.princip.getp.domain.auth.RefreshToken;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "token_verification")
public class RefreshTokenRedisEntity {

    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public static RefreshTokenRedisEntity from(final RefreshToken token) {
        final RefreshTokenRedisEntity entity = new RefreshTokenRedisEntity();
        entity.memberId = token.getMemberId();
        entity.refreshToken = token.getRefreshToken();
        entity.expiration = token.getExpiration();
        return entity;
    }
}
