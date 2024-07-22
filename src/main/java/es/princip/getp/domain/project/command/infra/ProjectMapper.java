package es.princip.getp.domain.project.command.infra;

import es.princip.getp.domain.common.domain.URL;
import es.princip.getp.domain.common.infra.URLMapper;
import es.princip.getp.domain.project.command.application.command.ApplyProjectCommand;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {URLMapper.class})
public interface ProjectMapper {

    ApplyProjectCommand toCommand(Long memberId, Long projectId, ApplyProjectRequest request);

    AttachmentFile stringToAttachmentFile(URL url);
}
