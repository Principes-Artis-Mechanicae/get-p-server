package es.princip.getp.domain.client.dto.response;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.global.domain.Address;

public record ProjectClientResponse(
    Long clientId,
    String nickname,
    String profileImageUri,
    Address address) {

    public static ProjectClientResponse from(final Client client) {
        return new ProjectClientResponse(
            client.getClientId(),
            client.getNickname(),
            client.getProfileImageUri(),
            client.getAddress()
        );
    }
}