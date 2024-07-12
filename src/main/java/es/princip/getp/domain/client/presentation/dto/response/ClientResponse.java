package es.princip.getp.domain.client.presentation.dto.response;

import es.princip.getp.domain.client.domain.Address;
import es.princip.getp.domain.client.domain.BankAccount;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.member.domain.model.Member;

import java.time.LocalDateTime;

public record ClientResponse(
    Long clientId,
    String nickname,
    String email,
    String phoneNumber,
    String profileImageUri,
    Address address,
    BankAccount bankAccount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ClientResponse from(final Client client, final Member member) {
        return new ClientResponse(
            client.getClientId(),
            member.getNickname().getValue(),
            client.getEmail().getValue(),
            member.getPhoneNumber().getValue(),
            member.getProfileImage().getUri().toString(),
            client.getAddress(),
            client.getBankAccount(),
            client.getCreatedAt(),
            client.getUpdatedAt()
        );
    }
}
