package es.princip.getp.domain.serviceTerm.application;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.exception.ServiceTermErrorCode;
import es.princip.getp.domain.serviceTerm.repository.ServiceTermRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceTermService {
    private final ServiceTermRepository serviceTermRepository;

    private ServiceTerm get(Optional<ServiceTerm> serviceTerm) {
        return serviceTerm.orElseThrow(() -> new BusinessLogicException(ServiceTermErrorCode.SERVICE_TERM_NOT_FOUND));
    }

    public ServiceTerm getByServiceTermId(Long serviceTermId) {
        return get(serviceTermRepository.findById(serviceTermId));
    }

    public ServiceTerm getByTag(String tag) {
        return get(serviceTermRepository.findByTag(tag));
    }

    @Transactional
    public ServiceTerm create(ServiceTermRequest serviceTermRequest) {
        return serviceTermRepository.save(serviceTermRequest.toEntity());
    }
  
    public boolean isAgreedAllRequiredServiceTerms(
        List<ServiceTermAgreementRequest> agreementRequests) {
        return agreementRequests.stream()
            .allMatch(agreementRequest -> {
                String tag = agreementRequest.tag();
                boolean agreed = agreementRequest.agreed();
                ServiceTerm serviceTerm = getByTag(tag);
                return !serviceTerm.isRequired() || agreed;
            });
    }
}
