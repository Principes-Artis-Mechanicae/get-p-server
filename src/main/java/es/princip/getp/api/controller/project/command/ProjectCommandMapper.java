package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.common.mapper.CommandMapper;
import es.princip.getp.api.controller.common.mapper.HashtagMapper;
import es.princip.getp.api.controller.common.mapper.PhoneNumberMapper;
import es.princip.getp.api.controller.common.mapper.URLMapper;
import es.princip.getp.api.controller.people.command.PeopleCommandMapper;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.commission.command.CommissionProjectCommand;
import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.URL;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@CommandMapper
@Mapper(
    componentModel = "spring",
    uses = {URLMapper.class, HashtagMapper.class, PhoneNumberMapper.class, PeopleCommandMapper.class}
)
interface ProjectCommandMapper {

    ApplyProjectCommand mapToCommand(MemberId memberId, ProjectId projectId, ApplyProjectRequest request);

    AttachmentFile mapToAttachmentFile(URL url);

    CommissionProjectCommand mapToCommand(MemberId memberId, CommissionProjectRequest request);

    @Mapping(source = "request.applicantId", target = "applicantId.value")
    ScheduleMeetingCommand mapToCommand(MemberId memberId, ProjectId projectId, ScheduleMeetingRequest request);
}
