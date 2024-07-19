package es.princip.getp.domain.project.query.dto;

import es.princip.getp.domain.client.command.domain.Address;

public record ProjectClientResponse(
    Long clientId,
    String nickname,
    Address address
) {
}