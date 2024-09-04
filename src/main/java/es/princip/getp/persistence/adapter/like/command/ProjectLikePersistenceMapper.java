package es.princip.getp.persistence.adapter.like.command;

import org.mapstruct.Mapper;

import es.princip.getp.domain.like.model.project.ProjectLike;
import es.princip.getp.persistence.adapter.like.command.project.ProjectLikeJpaEntity;

@Mapper(componentModel = "spring")
public interface ProjectLikePersistenceMapper {
    ProjectLike mapToDomain(ProjectLikeJpaEntity projectLikeJpaEntity);

    ProjectLikeJpaEntity mapToJpa(ProjectLike projectLike);
}
