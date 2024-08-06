package es.princip.getp.domain.like.command.domain.project;

import es.princip.getp.domain.like.command.domain.LikeReceivable;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.like.command.domain.Liker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectLiker extends Liker<ProjectLike> {

    private final ProjectLikeRepository projectLikeRepository;

    @Autowired
    public ProjectLiker(ProjectLikeRepository projectLikeRepository) {
        super(projectLikeRepository);
        this.projectLikeRepository = projectLikeRepository;
    }

    @Override
    public ProjectLike like(final Likeable likeable, final LikeReceivable likeReceivable) {
        checkAlreadyLiked(likeable.getId(), likeReceivable.getId());
        final ProjectLike like = ProjectLike.of(likeable.getId(), likeReceivable.getId());
        projectLikeRepository.save(like);
        return like;
    }
}
