package es.princip.getp.domain.auth.presentation.dto.request;

import es.princip.getp.domain.member.command.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import jakarta.validation.constraints.NotNull;

public record ServiceTermAgreementRequest(
    @NotNull String tag, 
    @NotNull Boolean agreed
) {

    public ServiceTermAgreementCommand toCommand() {
        ServiceTermTag tag = ServiceTermTag.of(this.tag);
        return new ServiceTermAgreementCommand(tag, this.agreed);
    }
}