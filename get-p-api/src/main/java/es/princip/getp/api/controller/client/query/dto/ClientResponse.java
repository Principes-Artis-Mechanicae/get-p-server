package es.princip.getp.api.controller.client.query.dto;

import es.princip.getp.domain.client.model.Address;

import java.time.LocalDateTime;

public record ClientResponse(
    Long clientId,
    String nickname,
    String phoneNumber,
    String email,
    String profileImageUri,
    Address address,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
