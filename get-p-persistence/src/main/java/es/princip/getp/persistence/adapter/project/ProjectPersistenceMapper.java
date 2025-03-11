package es.princip.getp.persistence.adapter.project;

import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.persistence.adapter.common.mapper.AttachmentFilePersistenceMapper;
import es.princip.getp.persistence.adapter.common.mapper.HashtagPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.ProjectJpaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        AttachmentFilePersistenceMapper.class,
        HashtagPersistenceMapper.class
    }
)
public interface ProjectPersistenceMapper {

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "clientId", target = "clientId.value")
    Project mapToDomain(ProjectJpaEntity projectJpaEntity);

    @InheritInverseConfiguration
    ProjectJpaEntity mapToJpa(Project project);
}
