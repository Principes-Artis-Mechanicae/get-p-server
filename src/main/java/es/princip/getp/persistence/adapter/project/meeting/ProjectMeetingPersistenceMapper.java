package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.domain.project.meeting.model.ProjectMeeting;
import es.princip.getp.persistence.adapter.common.mapper.PhoneNumberPersistenceMapper;
import es.princip.getp.persistence.adapter.project.meeting.model.ProjectMeetingJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhoneNumberPersistenceMapper.class})
public interface ProjectMeetingPersistenceMapper {

    @Mapping(source = "peopleId", target = "applicantId.value")
    ProjectMeeting mapToDomain(ProjectMeetingJpaEntity projectMeetingJpaEntity);

    @Mapping(target = "peopleId", source = "applicantId.value")
    @Mapping(target = "phoneNumber", source = "phoneNumber.value")
    ProjectMeetingJpaEntity mapToJpa(ProjectMeeting projectMeeting);
}
