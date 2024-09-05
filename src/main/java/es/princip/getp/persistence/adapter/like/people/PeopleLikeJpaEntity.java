package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "people_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleLikeJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "people_id")
    private Long peopleId;

    @Builder
    public PeopleLikeJpaEntity(
        final Long id,
        final Long clientId,
        final Long peopleId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.clientId = clientId;
        this.peopleId = peopleId;
    }
}