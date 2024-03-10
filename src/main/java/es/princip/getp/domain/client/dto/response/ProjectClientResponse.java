package es.princip.getp.domain.client.dto.response;

import jakarta.validation.constraints.NotNull;
import es.princip.getp.domain.client.domain.entity.Client;

public record ProjectClientResponse(
    @NotNull Long clientId,
    @NotNull String nickname,
    @NotNull String profileImageUri,
    @NotNull String address
) {

    public static ProjectClientResponse from(Client client) {
        return new ProjectClientResponse(
            client.getClientId(),
            client.getNickname(),
            client.getProfileImageUri(),
            client.getAddress()
        );
    }
}