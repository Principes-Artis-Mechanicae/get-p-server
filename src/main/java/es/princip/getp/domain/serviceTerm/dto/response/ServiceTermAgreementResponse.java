package es.princip.getp.domain.serviceTerm.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTermAgreement;
import jakarta.validation.constraints.NotNull;

public record ServiceTermAgreementResponse(
    @NotNull String tag, 
    boolean required, 
    boolean agreed, 
    boolean revocable, 
    @NotNull LocalDateTime agreedAt
) {
    private static ServiceTermAgreementResponse from(ServiceTermAgreement agreement) {
        return new ServiceTermAgreementResponse(
            agreement.getServiceTerm().getTag(),
            agreement.getServiceTerm().isRequired(),
            agreement.isAgreed(), 
            agreement.getServiceTerm().isRevocable(), 
            agreement.getAgreedAt()
        );
    }

    public static List<ServiceTermAgreementResponse> from(List<ServiceTermAgreement> agreements) {
        return agreements.stream().map(ServiceTermAgreementResponse::from).collect(Collectors.toList());
    }
}