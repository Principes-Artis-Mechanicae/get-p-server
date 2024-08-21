package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "service_term")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ServiceTermJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "service_term_tag")
    private String tag;

    @Column(name = "required")
    private boolean required;

    @Column(name = "revocable")
    private boolean revocable;
}
