package es.princip.getp.application.project.commission.port.in;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetProjectQuery {

    Page<ProjectCardResponse> getPagedCards(Pageable pageable);

    ProjectDetailResponse getDetailBy(ProjectId projectId);
}