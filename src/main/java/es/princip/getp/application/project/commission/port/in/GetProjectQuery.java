package es.princip.getp.application.project.commission.port.in;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.project.commission.command.GetProjectCommand;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.springframework.data.domain.Page;

public interface GetProjectQuery {

    Page<ProjectCardResponse> getPagedCards(GetProjectCommand command);

    ProjectDetailResponse getDetailBy(Member member, ProjectId projectId);
}