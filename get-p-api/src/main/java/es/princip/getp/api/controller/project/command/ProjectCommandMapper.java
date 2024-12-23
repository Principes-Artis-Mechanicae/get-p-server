package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.common.mapper.HashtagMapper;
import es.princip.getp.api.controller.common.mapper.PhoneNumberMapper;
import es.princip.getp.api.controller.common.mapper.URLMapper;
import es.princip.getp.api.controller.people.command.PeopleCommandMapper;
import es.princip.getp.api.controller.project.command.dto.request.*;
import es.princip.getp.application.project.apply.dto.command.ApplyProjectAsIndividualCommand;
import es.princip.getp.application.project.apply.dto.command.ApplyProjectAsTeamCommand;
import es.princip.getp.application.project.apply.dto.command.ApplyProjectCommand;
import es.princip.getp.application.project.commission.dto.command.CommissionProjectCommand;
import es.princip.getp.application.project.confirmation.dto.command.ConfirmationProjectCommand;
import es.princip.getp.application.project.meeting.dto.command.ScheduleMeetingCommand;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.URL;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {URLMapper.class, HashtagMapper.class, PhoneNumberMapper.class, PeopleCommandMapper.class}
)
abstract class ProjectCommandMapper {

    ApplyProjectCommand mapToCommand(
            Member member,
            ProjectId projectId,
            ApplyProjectRequest request
    ) {
        if (request instanceof ApplyProjectAsIndividualRequest req) {
            return mapToCommand(member, projectId, req);
        } else if (request instanceof ApplyProjectAsTeamRequest req) {
            return mapToCommand(member, projectId, req);
        } else {
            throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + request.getClass());
        }
    }

    @Mapping(source = "request.expectedDuration", target = "expectedDuration")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.attachmentFiles", target = "attachmentFiles")
    protected abstract ApplyProjectAsIndividualCommand mapToCommand(
            Member member,
            ProjectId projectId,
            ApplyProjectAsIndividualRequest request
    );

    @Mapping(source = "request.expectedDuration", target = "expectedDuration")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.attachmentFiles", target = "attachmentFiles")
    @Mapping(source = "request.teammates", target = "teammates")
    protected abstract ApplyProjectAsTeamCommand mapToCommand(
            Member member,
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

    @Mapping(source = "request.applicantId", target = "applicantId.value")
    abstract ConfirmationProjectCommand mapToCommand(
            MemberId memberId,
            ProjectId projectId,
            ConfirmProjectRequest request
    );
}
