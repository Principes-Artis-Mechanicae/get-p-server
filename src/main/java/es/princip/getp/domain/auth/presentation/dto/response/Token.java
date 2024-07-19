package es.princip.getp.domain.auth.presentation.dto.response;

public record Token(
    String grantType,
    String accessToken,
    String refreshToken
) {
}