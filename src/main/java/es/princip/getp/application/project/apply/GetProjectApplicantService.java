package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.project.apply.exception.NotMyProjectException;
import es.princip.getp.application.project.apply.port.in.GetProjectApplicantQuery;
import es.princip.getp.application.project.apply.port.out.FindProjectApplicantPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProjectApplicantService implements GetProjectApplicantQuery {

    private final FindProjectApplicantPort findProjectApplicantPort;
    private final LoadClientPort loadClientPort;
    private final LoadProjectPort loadProjectPort;

    @Override
    public Page<DetailPeopleResponse> getPagedDetails(
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
        return findProjectApplicantPort.findBy(projectId, pageable);
    }
}
