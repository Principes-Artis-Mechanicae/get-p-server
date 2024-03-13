package es.princip.getp.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record Token(
    String grantType,
    String accessToken,
    String refreshToken
) {
}