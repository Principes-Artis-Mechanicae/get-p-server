package es.princip.getp.domain.client.presentation.dto.response;

import es.princip.getp.domain.client.domain.Address;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.member.domain.model.Member;

public record ProjectClientResponse(
    Long clientId,
    String nickname,
    String profileImageUri,
    Address address
) {

    public static ProjectClientResponse from(final Client client, final Member member) {
        return new ProjectClientResponse(
            client.getClientId(),
            member.getNickname().getValue(),
            member.getProfileImage().getUri().toString(),
            client.getAddress()
        );
    }
}