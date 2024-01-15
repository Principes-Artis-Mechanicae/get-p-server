package es.princip.getp.domain.serviceTerm.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.entity.ServiceTerm;
import es.princip.getp.domain.serviceTerm.exception.ServiceTermErrorCode;
import es.princip.getp.domain.serviceTerm.repository.ServiceTermRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;

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
}
