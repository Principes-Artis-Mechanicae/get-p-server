package es.princip.getp.domain.like.command.domain.project;

import es.princip.getp.domain.like.command.domain.Like;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorColumn
@Table(name = "project_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AttributeOverrides(
    {
        @AttributeOverride(name = "id", column = @Column(name = "project_like_id")),
        @AttributeOverride(name = "likerId", column = @Column(name = "people_id")),
        @AttributeOverride(name = "likedId", column = @Column(name = "project_id"))
    }
)
public class ProjectLike extends Like {

    private ProjectLike(final Long peopleId, final Long projectId) {
        super(peopleId, projectId);
    }

    public static ProjectLike of(final Long peopleId, final Long projectId) {
        return new ProjectLike(peopleId, projectId);
    }
}