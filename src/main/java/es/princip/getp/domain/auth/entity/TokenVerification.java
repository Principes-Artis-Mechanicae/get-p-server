package es.princip.getp.domain.auth.entity;

import es.princip.getp.global.security.provider.JwtTokenProvider;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "token_verification")
public class TokenVerification {
    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long expiration;

    public TokenVerification(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiration = JwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME();
    }
}
