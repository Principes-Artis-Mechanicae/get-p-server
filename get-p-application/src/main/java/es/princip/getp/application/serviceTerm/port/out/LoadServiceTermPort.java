package es.princip.getp.application.serviceTerm.port.out;

import es.princip.getp.domain.serviceTerm.model.ServiceTerm;

import java.util.List;

public interface LoadServiceTermPort {

    List<ServiceTerm> loadAllBy(boolean required);
}
