package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.domain.like.project.model.ProjectLike;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectLikePersistenceMapper {

    @Mapping(source = "projectId", target = "projectId.value")
    @Mapping(source = "peopleId", target = "peopleId.value")
    ProjectLike mapToDomain(ProjectLikeJpaEntity projectLikeJpaEntity);

    @InheritInverseConfiguration
    ProjectLikeJpaEntity mapToJpa(ProjectLike projectLike);
}
