package es.princip.getp.application.project.commission.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.application.common.dto.response.AddressResponse;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ProjectClientResponse(
    Long clientId,
    String nickname,
    AddressResponse address
) {
}