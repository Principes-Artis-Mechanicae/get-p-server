package es.princip.getp.domain.serviceTerm.dto.reqeust;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import jakarta.validation.constraints.NotNull;

public record ServiceTermRequest(
    @NotNull String tag,
    boolean required, 
    boolean revocable
) {
    public ServiceTerm toEntity() {
        return ServiceTerm.builder().tag(tag).required(required()).revocable(revocable).build();
    }
}