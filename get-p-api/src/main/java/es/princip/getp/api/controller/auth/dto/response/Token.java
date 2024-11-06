package es.princip.getp.api.controller.auth.dto.response;

public record Token(
    String grantType,
    String accessToken,
    String refreshToken
) {
}