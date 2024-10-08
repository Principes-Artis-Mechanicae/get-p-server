package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.domain.client.model.Address;

public record ProjectClientResponse(
    Long clientId,
    String nickname,
    Address address
) {
}