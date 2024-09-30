package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.domain.project.apply.model.Teammate;
import es.princip.getp.persistence.adapter.common.mapper.AttachmentFilePersistenceMapper;
import es.princip.getp.persistence.adapter.people.mapper.PeoplePersistenceMapper;
import es.princip.getp.persistence.adapter.project.apply.model.IndividualProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.ProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.TeamProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.TeammateJpaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        PeoplePersistenceMapper.class,
        AttachmentFilePersistenceMapper.class
    }
)
abstract class ProjectApplicationPersistenceMapper {

    ProjectApplication mapToDomain(ProjectApplicationJpaEntity application) {
        if (application instanceof IndividualProjectApplicationJpaEntity individual) {
            return mapToDomain(individual);
        } else if (application instanceof TeamProjectApplicationJpaEntity team) {
            return mapToDomain(team);
        }
        throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + application.getClass());
    }

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "applicantId", target = "applicantId.value")
    @Mapping(source = "projectId", target = "projectId.value")
    protected abstract TeamProjectApplication mapToDomain(TeamProjectApplicationJpaEntity application);

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "applicantId", target = "applicantId.value")
    @Mapping(source = "projectId", target = "projectId.value")
    protected abstract IndividualProjectApplication mapToDomain(IndividualProjectApplicationJpaEntity application);

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "peopleId", target = "peopleId.value")
    protected abstract Teammate mapToDomain(TeammateJpaEntity application);

    ProjectApplicationJpaEntity mapToJpa(ProjectApplication application) {
        if (application instanceof IndividualProjectApplication individual) {
            return mapToJpa(individual);
        } else if (application instanceof TeamProjectApplication team) {
            final TeamProjectApplicationJpaEntity entity = mapToJpa(team);
            entity.getTeammates().addAll(team.getTeammates().stream()
                .map(teammate -> mapToJpa(teammate, entity))
                .toList());
            return mapToJpa(team);
        }
        throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + application.getClass());
    }

    @InheritInverseConfiguration
    protected abstract TeamProjectApplicationJpaEntity mapToJpa(TeamProjectApplication domain);

    @InheritInverseConfiguration
    protected abstract IndividualProjectApplicationJpaEntity mapToJpa(IndividualProjectApplication domain);

    @Mapping(target = "id", source = "teammate.id.value")
    @Mapping(target = "peopleId", source = "teammate.peopleId.value")
    @Mapping(target = "status", source = "teammate.status")
    protected abstract TeammateJpaEntity mapToJpa(Teammate teammate, TeamProjectApplicationJpaEntity application);
}
