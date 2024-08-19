package es.princip.getp.api.controller.serviceTerm.dto.response;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import jakarta.validation.constraints.NotNull;

public record ServiceTermResponse(
    @NotNull String tag, 
    boolean required, 
    boolean revocable
) {
    public static ServiceTermResponse from(ServiceTerm serviceTerm) {
        return new ServiceTermResponse(
            serviceTerm.getTag().getValue(),
            serviceTerm.isRequired(),
            serviceTerm.isRevocable()
        );
    }
}