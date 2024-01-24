package es.princip.getp.domain.client.dto.response;

import java.time.LocalDateTime;
import es.princip.getp.domain.client.entity.Client;
import jakarta.validation.constraints.NotNull;

public record ClientResponse(@NotNull Long Id, @NotNull String name, @NotNull String email,
        @NotNull String phoneNumber, @NotNull String profileImageUri, @NotNull String address,
        @NotNull String accountNumber, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(client.getClientId(), client.getName(), client.getEmail(), client.getPhoneNumber(),
        client.getProfileImageUri(), client.getAddress(), client.getAccountNumber(),
        client.getCreatedAt(), client.getUpdateAt());
    }
}
