package es.princip.getp.application.serviceTerm.port.out;

import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;

import java.util.Set;

public interface CheckServiceTermPort {

    void existsBy(Set<ServiceTermTag> tag);

    boolean existsBy(ServiceTermTag tag);
}
