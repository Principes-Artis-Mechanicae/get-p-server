package es.princip.getp.application.project.commission.dto.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.application.common.dto.response.AddressResponse;

public record ProjectClientResponse(
    @JsonInclude(NON_NULL) Long clientId,
    String nickname,
    AddressResponse address
) {
}