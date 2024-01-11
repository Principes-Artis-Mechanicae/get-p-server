package es.princip.getp.domain.serviceTerm.dto.response;

import es.princip.getp.domain.serviceTerm.entity.ServiceTerm;
import jakarta.validation.constraints.NotNull;

public record ServiceTermResponse(
    @NotNull String tag, 
    boolean required, 
    boolean revocable
) {
    public static ServiceTermResponse from(ServiceTerm serviceTerm) {
        return new ServiceTermResponse(serviceTerm.getTag(), serviceTerm.isRequired(), serviceTerm.isRevocable()); 
    }
}