package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.api.controller.common.dto.AddressResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectClientResponse(
    Long clientId,
    String nickname,
    AddressResponse address
) {
}