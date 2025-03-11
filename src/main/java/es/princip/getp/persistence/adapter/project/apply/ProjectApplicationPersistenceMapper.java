package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.persistence.adapter.common.mapper.AttachmentFilePersistenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AttachmentFilePersistenceMapper.class})
interface ProjectApplicationPersistenceMapper {

    @Mapping(source = "applicantId", target = "applicantId")
    ProjectApplication mapToDomain(ProjectApplicationJpaEntity applicationJpaEntity);

    @Mapping(target = "applicantId", source = "applicantId")
    ProjectApplicationJpaEntity mapToJpa(ProjectApplication application);
}
