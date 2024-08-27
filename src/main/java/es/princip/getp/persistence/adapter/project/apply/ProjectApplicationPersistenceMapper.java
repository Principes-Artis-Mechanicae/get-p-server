package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.persistence.adapter.common.mapper.AttachmentFilePersistenceMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AttachmentFilePersistenceMapper.class})
interface ProjectApplicationPersistenceMapper {

    ProjectApplication mapToDomain(ProjectApplicationJpaEntity applicationJpaEntity);

    ProjectApplicationJpaEntity mapToJpa(ProjectApplication application);
}
