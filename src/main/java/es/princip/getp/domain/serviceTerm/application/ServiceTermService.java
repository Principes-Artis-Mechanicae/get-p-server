package es.princip.getp.domain.serviceTerm.application;

import es.princip.getp.api.controller.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import es.princip.getp.domain.serviceTerm.exception.DuplicatedTagException;
import es.princip.getp.domain.serviceTerm.port.out.CheckServiceTermPort;
import es.princip.getp.domain.serviceTerm.port.out.SaveServiceTermPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceTermService {

    private final CheckServiceTermPort checkServiceTermPort;
    private final SaveServiceTermPort saveServiceTermPort;

    @Transactional
    public ServiceTerm register(final ServiceTermRequest request) {
        final ServiceTermTag tag = ServiceTermTag.of(request.tag());
        if (checkServiceTermPort.existsBy(tag)) {
            throw new DuplicatedTagException();
        }
        final ServiceTerm serviceTerm = new ServiceTerm(tag, request.required(), request.revocable());
        saveServiceTermPort.save(serviceTerm);
        return serviceTerm;
    }
}
