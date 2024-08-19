package es.princip.getp.api.controller.client.query.dto;

import es.princip.getp.domain.client.command.domain.Address;
import es.princip.getp.domain.client.command.domain.BankAccount;
import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.member.model.Member;

import java.time.LocalDateTime;

public record ClientResponse(
    Long clientId,
    String nickname,
    String phoneNumber,
    String email,
    String profileImageUri,
    Address address,
    BankAccount bankAccount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ClientResponse of(final Client client, final Member member) {
        return new ClientResponse(
            client.getClientId(),
            member.getNickname().getValue(),
            client.getEmail().getValue(),
            member.getPhoneNumber().getValue(),
            member.getProfileImage().getUrl(),
            client.getAddress(),
            client.getBankAccount(),
            client.getCreatedAt(),
            client.getUpdatedAt()
        );
    }
}
