package es.princip.getp.api.controller.project.query.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.api.controller.common.dto.AddressResponse;

public record ProjectClientResponse(
    @JsonInclude(NON_NULL) Long clientId,
    String nickname,
    AddressResponse address
) {
}