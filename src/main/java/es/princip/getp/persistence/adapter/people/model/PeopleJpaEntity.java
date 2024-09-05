package es.princip.getp.persistence.adapter.people.model;

import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "people_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    private PeopleType peopleType;

    @Embedded
    private PeopleProfileJpaVO profile;

    @PostLoad
    private void afterLoad() {
        if (profile.isNotRegistered()) {
            this.profile = null;
        }
    }

    @Builder
    public PeopleJpaEntity(
        final Long id,
        final Long memberId,
        final String email,
        final PeopleType peopleType,
        final PeopleProfileJpaVO profile,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.memberId = memberId;
        this.email = email;
        this.peopleType = peopleType;
        this.profile = profile;
    }
}
