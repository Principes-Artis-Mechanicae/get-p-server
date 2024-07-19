package es.princip.getp.domain.serviceTerm.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_term")
public class ServiceTerm extends BaseTimeEntity {

    @EmbeddedId
    private ServiceTermTag tag;

    @Getter
    @Column(name = "required")
    private boolean required;
    
    @Column(name = "revocable")
    private boolean revocable;

    public ServiceTerm(ServiceTermTag tag, boolean required, boolean revocable) {
        this.tag = tag;
        this.required = required;
        this.revocable = revocable;
    }
}
