package es.princip.getp.domain.serviceTerm.port.out;

import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;

public interface CheckServiceTermPort {

    boolean existsBy(ServiceTermTag tag);
}
