package es.princip.getp.persistence.adapter.project;

import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.persistence.adapter.common.mapper.AttachmentFilePersistenceMapper;
import es.princip.getp.persistence.adapter.common.mapper.HashtagPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.ProjectJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AttachmentFilePersistenceMapper.class, HashtagPersistenceMapper.class})
public interface ProjectPersistenceMapper {

    Project mapToDomain(ProjectJpaEntity projectJpaEntity);

    ProjectJpaEntity mapToJpa(Project project);
}
