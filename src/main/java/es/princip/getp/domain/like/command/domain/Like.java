package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long likerId;

    private Long likedId;

    protected Like(final Long likerId, final Long likedId) {
        this.likerId = likerId;
        this.likedId = likedId;
    }
}
