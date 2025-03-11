package es.princip.getp.persistence.adapter.like.people;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "people_like")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleLikeJpaEntity {

    @Id
    @Column(name = "people_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "people_id")
    private Long peopleId;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime createdAt;

    @Builder
    public PeopleLikeJpaEntity(
        final Long id,
        final Long memberId,
        final Long peopleId,
        final LocalDateTime createdAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.peopleId = peopleId;
        this.createdAt = createdAt;
    }
}