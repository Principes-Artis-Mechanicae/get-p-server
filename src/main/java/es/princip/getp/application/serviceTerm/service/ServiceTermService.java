package es.princip.getp.application.serviceTerm.service;

import es.princip.getp.api.controller.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.application.serviceTerm.exception.DuplicatedTagException;
import es.princip.getp.application.serviceTerm.port.in.RegisterServiceTermUseCase;
import es.princip.getp.application.serviceTerm.port.out.CheckServiceTermPort;
import es.princip.getp.application.serviceTerm.port.out.SaveServiceTermPort;
import es.princip.getp.domain.serviceTerm.model.ServiceTerm;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceTermService implements RegisterServiceTermUseCase {

    private final CheckServiceTermPort checkServiceTermPort;
    private final SaveServiceTermPort saveServiceTermPort;

    @Override
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
