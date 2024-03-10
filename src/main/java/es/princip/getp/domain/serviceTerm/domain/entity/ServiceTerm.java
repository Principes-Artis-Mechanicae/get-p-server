package es.princip.getp.domain.serviceTerm.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_term")
public class ServiceTerm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_term_id")
    private Long serviceTermId;

    @Column(name = "tag")
    private String tag;
    
    @Column(name = "required")
    private boolean required;
    
    @Column(name = "revocable")
    private boolean revocable;

    @Builder
    public ServiceTerm(String tag, boolean required, boolean revocable) {
        this.tag = tag;
        this.required = required;
        this.revocable = revocable;
    }
}
