package es.princip.getp.persistence.adapter.project.confirmation;

import es.princip.getp.domain.project.confirmation.model.ProjectConfirmation;
import es.princip.getp.persistence.adapter.project.confirmation.model.ProjectConfirmationJpaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectConfirmationPersistenceMapper {

    @Mapping(source = "projectId", target = "projectId.value")
    @Mapping(source = "applicantId", target = "applicantId.value")
    ProjectConfirmation mapToDomain(ProjectConfirmationJpaEntity projectConfirmationJpaEntity);

    @InheritInverseConfiguration
    ProjectConfirmationJpaEntity mapToJpa(ProjectConfirmation projectConfirmation);
}
