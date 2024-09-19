package es.princip.getp.application.project.commission;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.project.commission.port.in.GetProjectQuery;
import es.princip.getp.application.project.commission.port.out.FindProjectPort;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProjectService implements GetProjectQuery {

    private final FindProjectPort findProjectPort;

    @Override
    public Page<ProjectCardResponse> getPagedCards(final Pageable pageable) {
        return findProjectPort.findBy(pageable);
    }

    @Override
    public ProjectDetailResponse getDetailBy(final ProjectId projectId) {
        return findProjectPort.findBy(projectId);
    }
}
