package es.princip.getp.domain.project.query.application;

import es.princip.getp.api.controller.PageResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.exception.NotMyProjectException;
import es.princip.getp.domain.project.query.dao.ProjectApplicantDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectApplicantService {

    private final ProjectApplicantDao projectApplicantDao;
    private final LoadClientPort loadClientPort;
    private final LoadProjectPort loadProjectPort;

    public PageResponse<DetailPeopleResponse> getApplicants(
        final Long projectId,
        final Member member,
        final Pageable pageable
    ) {
        final Long memberId = member.getMemberId();
        final Client client = loadClientPort.loadBy(memberId);
        final Project project = loadProjectPort.loadBy(projectId);
        if (!project.isClient(client)) {
            throw new NotMyProjectException();
        }
        return PageResponse.from(projectApplicantDao.findPagedApplicantByProjectId(projectId, pageable));
    }
}
