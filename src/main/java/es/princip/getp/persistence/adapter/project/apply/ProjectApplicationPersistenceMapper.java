package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.persistence.adapter.common.mapper.AttachmentFilePersistenceMapper;
import es.princip.getp.persistence.adapter.people.mapper.PeoplePersistenceMapper;
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

    ProjectApplication mapToDomain(ProjectApplicationJpaEntity jpa) {
        if (jpa instanceof IndividualProjectApplicationJpaEntity domain) {
            return mapToDomain(domain);
        } else if (jpa instanceof TeamProjectApplicationJpaEntity domain) {
            return mapToDomain(domain);
        }
        throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + jpa.getClass());
    }

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "applicantId", target = "applicantId.value")
    @Mapping(source = "projectId", target = "projectId.value")
    protected abstract TeamProjectApplication mapToDomain(TeamProjectApplicationJpaEntity jpa);

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "applicantId", target = "applicantId.value")
    @Mapping(source = "projectId", target = "projectId.value")
    protected abstract IndividualProjectApplication mapToDomain(IndividualProjectApplicationJpaEntity jpa);

    ProjectApplicationJpaEntity mapToJpa(ProjectApplication domain) {
        if (domain instanceof IndividualProjectApplication jpa) {
            return mapToJpa(jpa);
        } else if (domain instanceof TeamProjectApplication jpa) {
            return mapToJpa(jpa);
        }
        throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + domain.getClass());
    }

    @InheritInverseConfiguration
    protected abstract TeamProjectApplicationJpaEntity mapToJpa(TeamProjectApplication domain);

    @InheritInverseConfiguration
    protected abstract IndividualProjectApplicationJpaEntity mapToJpa(IndividualProjectApplication domain);
}
