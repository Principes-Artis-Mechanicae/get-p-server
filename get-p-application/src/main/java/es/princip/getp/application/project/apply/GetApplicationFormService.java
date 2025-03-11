package es.princip.getp.application.project.apply;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.project.commission.dto.response.ProjectDetailResponse;
import es.princip.getp.application.project.apply.exception.NotMyProjectException;
import es.princip.getp.application.project.apply.port.in.GetApplicationFormQuery;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.commission.port.out.FindProjectPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetApplicationFormService implements GetApplicationFormQuery {

    private final FindProjectPort findProjectPort;

    private final LoadProjectPort loadProjectPort;
    private final LoadClientPort loadClientPort;
    private final LoadProjectApplicantPort loadApplicationPort;

    private final ProjectApplicationFormResponseFactory responseFactory;

    @Override
    public es.princip.getp.application.project.apply.dto.response.ProjectApplicationFormResponse getApplicationFormBy(
        final Member member,
        final ProjectApplicationId applicationId
    ) {
        final Client client = loadClientPort.loadBy(member.getId());
        final ProjectApplication application = loadApplicationPort.loadBy(applicationId);
        final Project project = loadProjectPort.loadBy(application.getProjectId());
        if (!project.isClient(client)) {
            throw new NotMyProjectException();
        }
        final ProjectDetailResponse projectDetailResponse = findProjectPort.findBy(member, application.getProjectId());
        return responseFactory.mapToResponse(application, projectDetailResponse);
    }
}
