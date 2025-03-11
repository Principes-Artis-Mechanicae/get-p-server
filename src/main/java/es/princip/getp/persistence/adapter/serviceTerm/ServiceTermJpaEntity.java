package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "service_term")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ServiceTermJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "service_term_tag")
    private String tag;

    @Column(name = "required")
    private boolean required;

    @Column(name = "revocable")
    private boolean revocable;

    @Builder
    public ServiceTermJpaEntity(
        final String tag,
        final boolean required,
        final boolean revocable,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.tag = tag;
        this.required = required;
        this.revocable = revocable;
    }
}
