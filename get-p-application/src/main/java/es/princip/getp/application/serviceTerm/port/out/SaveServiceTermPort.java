package es.princip.getp.application.serviceTerm.port.out;

import es.princip.getp.domain.serviceTerm.model.ServiceTerm;

public interface SaveServiceTermPort {

    void save(ServiceTerm serviceTerm);
}
