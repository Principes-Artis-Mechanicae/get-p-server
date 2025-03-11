package es.princip.getp.api.controller.serviceTerm.dto.reqeust;

import jakarta.validation.constraints.NotNull;

public record ServiceTermRequest(
    @NotNull String tag,
    boolean required, 
    boolean revocable
) {
}