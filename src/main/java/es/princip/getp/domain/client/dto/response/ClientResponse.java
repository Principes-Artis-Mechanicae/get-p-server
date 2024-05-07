package es.princip.getp.domain.client.dto.response;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.global.domain.values.Address;
import es.princip.getp.global.domain.values.BankAccount;

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
    LocalDateTime updatedAt) {

    public static ClientResponse from(final Client client) {
        return new ClientResponse(
            client.getClientId(),
            client.getNickname(),
            client.getEmail(),
            client.getPhoneNumber(),
            client.getProfileImageUri(),
            client.getAddress(),
            client.getBankAccount(),
            client.getCreatedAt(),
            client.getUpdatedAt());
    }
}
