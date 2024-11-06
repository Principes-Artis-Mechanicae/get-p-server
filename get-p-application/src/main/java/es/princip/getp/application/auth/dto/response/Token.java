package es.princip.getp.application.auth.dto.response;

public record Token(
    String grantType,
    String accessToken,
    String refreshToken
) {
}