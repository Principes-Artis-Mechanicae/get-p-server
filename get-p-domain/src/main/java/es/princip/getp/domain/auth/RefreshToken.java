package es.princip.getp.domain.auth;

import es.princip.getp.domain.member.model.MemberId;
import lombok.Getter;

@Getter
public class RefreshToken {

    private Long memberId;
    private String refreshToken;
    private Long expiration;

    public RefreshToken(final MemberId memberId, final String refreshToken, final Long expiration) {
        this.memberId = memberId.getValue();
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
