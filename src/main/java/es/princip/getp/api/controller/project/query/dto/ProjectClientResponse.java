package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.domain.client.model.Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectClientResponse(
    Long clientId,
    String nickname,
    Address address
) {
}