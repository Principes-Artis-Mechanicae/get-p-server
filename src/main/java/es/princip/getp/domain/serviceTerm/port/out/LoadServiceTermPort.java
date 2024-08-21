package es.princip.getp.domain.serviceTerm.port.out;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;

public interface LoadServiceTermPort {

    ServiceTerm loadBy(ServiceTermTag tag);
}
