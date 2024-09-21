package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.common.mapper.CommandMapper;
import es.princip.getp.api.controller.common.mapper.HashtagMapper;
import es.princip.getp.api.controller.common.mapper.PhoneNumberMapper;
import es.princip.getp.api.controller.common.mapper.URLMapper;
import es.princip.getp.api.controller.people.command.PeopleCommandMapper;
import es.princip.getp.api.controller.project.command.dto.request.*;
import es.princip.getp.application.project.apply.command.ApplyProjectAsIndividualCommand;
import es.princip.getp.application.project.apply.command.ApplyProjectAsTeamCommand;
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
abstract class ProjectCommandMapper {

    ApplyProjectCommand mapToCommand(
        MemberId memberId,
        ProjectId projectId,
        ApplyProjectRequest request
    ) {
        if (request instanceof ApplyProjectAsIndividualRequest req) {
            return mapToCommand(memberId, projectId, req);
        } else if (request instanceof ApplyProjectAsTeamRequest req) {
            return mapToCommand(memberId, projectId, req);
        } else {
            throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + request.getClass());
        }
    }

    @Mapping(source = "request.expectedDuration", target = "data.expectedDuration")
    @Mapping(source = "request.description", target = "data.description")
    @Mapping(source = "request.attachmentFiles", target = "data.attachmentFiles")
    protected abstract ApplyProjectAsIndividualCommand mapToCommand(
        MemberId memberId,
        ProjectId projectId,
        ApplyProjectAsIndividualRequest request
    );

    @Mapping(source = "request.expectedDuration", target = "data.expectedDuration")
    @Mapping(source = "request.description", target = "data.description")
    @Mapping(source = "request.attachmentFiles", target = "data.attachmentFiles")
    @Mapping(source = "request.teams", target = "data.teams")
    protected abstract ApplyProjectAsTeamCommand mapToCommand(
        MemberId memberId,
        ProjectId projectId,
        ApplyProjectAsTeamRequest request
    );

    protected abstract AttachmentFile mapToAttachmentFile(URL url);

    abstract CommissionProjectCommand mapToCommand(
        MemberId memberId,
        CommissionProjectRequest request
    );

    @Mapping(source = "request.applicantId", target = "applicantId.value")
    abstract ScheduleMeetingCommand mapToCommand(
        MemberId memberId,
        ProjectId projectId,
        ScheduleMeetingRequest request
    );
}
