package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.common.domain.URL;
import es.princip.getp.domain.common.infra.HashtagMapper;
import es.princip.getp.domain.common.infra.URLMapper;
import es.princip.getp.domain.project.command.application.command.ApplyProjectCommand;
import es.princip.getp.domain.project.command.application.command.RegisterProjectCommand;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import es.princip.getp.domain.project.command.presentation.dto.request.CommissionProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {URLMapper.class, HashtagMapper.class})
public interface ProjectCommandMapper {

    ApplyProjectCommand mapToCommand(Long memberId, Long projectId, ApplyProjectRequest request);

    AttachmentFile mapToAttachmentFile(URL url);

    RegisterProjectCommand mapToCommand(Long memberId, CommissionProjectRequest request);
}