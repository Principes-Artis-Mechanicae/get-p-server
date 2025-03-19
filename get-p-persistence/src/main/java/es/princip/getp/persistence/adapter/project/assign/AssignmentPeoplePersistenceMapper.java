package es.princip.getp.persistence.adapter.project.assign;

import es.princip.getp.domain.project.confirmation.model.AssignmentPeople;
import es.princip.getp.persistence.adapter.project.assign.model.AssignmentPeopleJpaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentPeoplePersistenceMapper {

    @Mapping(source = "projectId", target = "projectId.value")
    @Mapping(source = "applicantId", target = "applicantId.value")
    AssignmentPeople mapToDomain(AssignmentPeopleJpaEntity assignmentPeopleJpaEntity);

    @InheritInverseConfiguration
    AssignmentPeopleJpaEntity mapToJpa(AssignmentPeople assignmentPeople);
}
