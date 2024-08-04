package es.princip.getp.domain.serviceTerm.application;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermRepository;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.exception.DuplicatedTagException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceTermService {

    private final ServiceTermRepository serviceTermRepository;

    @Transactional
    public ServiceTerm create(ServiceTermRequest request) {
        ServiceTermTag tag = ServiceTermTag.of(request.tag());
        if (serviceTermRepository.existsByTag(tag)) {
            throw new DuplicatedTagException();
        }
        ServiceTerm serviceTerm = new ServiceTerm(tag, request.required(), request.revocable());
        return serviceTermRepository.save(serviceTerm);
    }
}
