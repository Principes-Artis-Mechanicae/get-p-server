package es.princip.getp.application.serviceTerm.port.in;

import es.princip.getp.api.controller.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.model.ServiceTerm;

public interface RegisterServiceTermUseCase {

    ServiceTerm register(ServiceTermRequest request);
}
