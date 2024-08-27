package es.princip.getp.domain.serviceTerm.model;

import es.princip.getp.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceTerm extends BaseEntity {

    private ServiceTermTag tag;
    private boolean required;
    private boolean revocable;
}
