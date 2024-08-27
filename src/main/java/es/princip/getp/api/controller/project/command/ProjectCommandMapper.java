package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.CommandMapper;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.commission.command.CommissionProjectCommand;
import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.common.domain.URL;
import es.princip.getp.common.infra.HashtagMapper;
import es.princip.getp.common.infra.PhoneNumberMapper;
import es.princip.getp.common.infra.URLMapper;
import es.princip.getp.domain.project.commission.model.AttachmentFile;
import org.mapstruct.Mapper;

@CommandMapper
@Mapper(componentModel = "spring", uses = {URLMapper.class, HashtagMapper.class, PhoneNumberMapper.class})
public interface ProjectCommandMapper {

    ApplyProjectCommand mapToCommand(Long memberId, Long projectId, ApplyProjectRequest request);

    AttachmentFile mapToAttachmentFile(URL url);

    CommissionProjectCommand mapToCommand(Long memberId, CommissionProjectRequest request);

    ScheduleMeetingCommand mapToCommand(Long memberId, Long projectId, ScheduleMeetingRequest request);
}
