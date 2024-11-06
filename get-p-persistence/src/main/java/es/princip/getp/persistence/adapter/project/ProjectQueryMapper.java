package es.princip.getp.persistence.adapter.project;

import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.persistence.adapter.common.mapper.DurationPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.ProjectJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        DurationPersistenceMapper.class
    }
)
public interface ProjectQueryMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.title", target = "title")
    @Mapping(source = "project.payment", target = "payment")
    @Mapping(source = "project.recruitmentCount", target = "recruitmentCount")
    @Mapping(source = "project.applicationDuration", target = "applicationDuration")
    @Mapping(source = "project.estimatedDuration", target = "estimatedDuration")
    @Mapping(source = "project.description", target = "description")
    @Mapping(source = "project.meetingType", target = "meetingType")
    @Mapping(source = "project.category", target = "category")
    @Mapping(source = "project.status", target = "status")
    @Mapping(source = "project.attachmentFiles", target = "attachmentFiles")
    @Mapping(source = "project.hashtags", target = "hashtags")
    ProjectDetailResponse mapToDetailResponse(
        ProjectJpaEntity project,
        Long applicantsCount,
        Long likesCount,
        Boolean liked,
        ProjectClientResponse client
    );
}
