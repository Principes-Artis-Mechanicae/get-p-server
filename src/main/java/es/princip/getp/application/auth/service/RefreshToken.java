package es.princip.getp.application.auth.service;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "token_verification")
public class RefreshToken {

    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    public RefreshToken(final Long memberId, final String refreshToken, final Long expiration) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
