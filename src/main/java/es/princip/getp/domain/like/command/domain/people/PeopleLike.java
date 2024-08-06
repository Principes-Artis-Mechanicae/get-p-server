package es.princip.getp.domain.like.command.domain.people;

import es.princip.getp.domain.like.command.domain.Like;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorColumn
@Table(name = "people_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AttributeOverrides(
    {
        @AttributeOverride(name = "id", column = @Column(name = "people_like_id")),
        @AttributeOverride(name = "likerId", column = @Column(name = "client_id")),
        @AttributeOverride(name = "likedId", column = @Column(name = "people_id"))
    }
)
public class PeopleLike extends Like {

    private PeopleLike(final Long clientId, final Long peopleId) {
        super(clientId, peopleId);
    }

    public static PeopleLike of(final Long clientId, final Long peopleId) {
        return new PeopleLike(clientId, peopleId);
    }
}