package es.princip.getp.application.project.commission.port.out;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindProjectPort {

    Page<ProjectCardResponse> findBy(Pageable pageable);

    ProjectDetailResponse findBy(Long projectId);
}
