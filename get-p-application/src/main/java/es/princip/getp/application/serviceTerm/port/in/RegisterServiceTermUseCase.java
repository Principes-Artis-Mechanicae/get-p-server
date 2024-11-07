package es.princip.getp.application.serviceTerm.port.in;

import es.princip.getp.application.serviceTerm.dto.command.ServiceTermCommand;
import es.princip.getp.domain.serviceTerm.model.ServiceTerm;

public interface RegisterServiceTermUseCase {

    ServiceTerm register(ServiceTermCommand command);
}
