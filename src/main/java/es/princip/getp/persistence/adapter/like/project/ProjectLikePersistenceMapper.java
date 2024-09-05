package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.domain.like.project.model.ProjectLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectLikePersistenceMapper {
    ProjectLike mapToDomain(ProjectLikeJpaEntity projectLikeJpaEntity);

    ProjectLikeJpaEntity mapToJpa(ProjectLike projectLike);
}
