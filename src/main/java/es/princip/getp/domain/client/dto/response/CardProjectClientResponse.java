package es.princip.getp.domain.client.dto.response;

import jakarta.validation.constraints.NotNull;
import es.princip.getp.domain.client.entity.Client;

public record CardProjectClientResponse(
    @NotNull Long clientId,
    @NotNull String nickname,
    @NotNull String profileImageUri,
    @NotNull String address
) {

    public static CardProjectClientResponse from(Client client) {
        return new CardProjectClientResponse(
            client.getClientId(),
            client.getNickname(),
            client.getProfileImageUri(),
            client.getAddress()
        );
    }
}