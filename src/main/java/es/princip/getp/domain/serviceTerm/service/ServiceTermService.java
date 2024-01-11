package es.princip.getp.domain.serviceTerm.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.entity.ServiceTerm;
import es.princip.getp.domain.serviceTerm.exception.ServiceTermNotFoundException;
import es.princip.getp.domain.serviceTerm.repository.ServiceTermRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceTermService {
    private final ServiceTermRepository serviceTermRepository;

    private ServiceTerm resolve(Optional<ServiceTerm> serviceTerm) {
        return serviceTerm.orElseThrow(() -> new ServiceTermNotFoundException());
    }

    public ServiceTerm getByServiceTermId(Long serviceTermId) {
        return resolve(serviceTermRepository.findById(serviceTermId));
    }

    public ServiceTerm getByTag(String tag) {
        return resolve(serviceTermRepository.findByTag(tag));
    }

    @Transactional
    public ServiceTerm create(ServiceTermRequest serviceTermRequest) {
        return serviceTermRepository.save(serviceTermRequest.toEntity());
    }
}
