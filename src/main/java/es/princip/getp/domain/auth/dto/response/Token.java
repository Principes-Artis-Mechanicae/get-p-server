package es.princip.getp.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Token(
    @NotNull String grantType,
    @NotNull String accessToken,
    @NotNull String refreshToken
) {
}