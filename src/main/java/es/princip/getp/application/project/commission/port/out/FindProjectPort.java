package es.princip.getp.application.project.commission.port.out;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.api.controller.project.query.dto.PublicProjectDetailResponse;
import es.princip.getp.application.project.commission.command.ProjectSearchFilter;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindProjectPort {

    Page<ProjectCardResponse> findBy(Pageable pageable, ProjectSearchFilter filter, MemberId memberId);

    ProjectDetailResponse findBy(ProjectId projectId);
    
    ProjectDetailResponse findBy(MemberId memberId, ProjectId projectId);

    PublicProjectDetailResponse findPublicDetailBy(ProjectId projectId);
}
