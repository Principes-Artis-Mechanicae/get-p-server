package es.princip.getp.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
public record Token(
    String grantType,
    String accessToken,
    String refreshToken
) {
}