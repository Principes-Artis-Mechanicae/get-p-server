package es.princip.getp.domain.like.command.domain.project;

import es.princip.getp.domain.like.command.domain.Unliker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectUnliker extends Unliker {

    @Autowired
    public ProjectUnliker(ProjectLikeRepository projectLikeRepository) {
        super(projectLikeRepository);
    }
}
