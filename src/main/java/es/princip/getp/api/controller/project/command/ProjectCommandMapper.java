package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.CommandMapper;
import es.princip.getp.api.controller.common.mapper.HashtagMapper;
import es.princip.getp.api.controller.common.mapper.PhoneNumberMapper;
import es.princip.getp.api.controller.common.mapper.URLMapper;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.commission.command.CommissionProjectCommand;
import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.URL;
import org.mapstruct.Mapper;

@CommandMapper
@Mapper(componentModel = "spring", uses = {URLMapper.class, HashtagMapper.class, PhoneNumberMapper.class})
interface ProjectCommandMapper {

    ApplyProjectCommand mapToCommand(Long memberId, Long projectId, ApplyProjectRequest request);

    AttachmentFile mapToAttachmentFile(URL url);

    CommissionProjectCommand mapToCommand(Long memberId, CommissionProjectRequest request);

    ScheduleMeetingCommand mapToCommand(Long memberId, Long projectId, ScheduleMeetingRequest request);
}
