package es.princip.getp.domain.serviceTerm.model;

import es.princip.getp.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceTerm extends BaseTimeEntity {

    private ServiceTermTag tag;
    private boolean required;
    private boolean revocable;
}
