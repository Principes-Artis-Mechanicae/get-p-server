package es.princip.getp.application.project.apply;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.dto.response.ProjectApplicationDetailResponse;
import es.princip.getp.application.project.apply.exception.NotMyProjectApplicationException;
import es.princip.getp.application.project.apply.port.in.GetApplicationDetailQuery;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.commission.dto.response.ProjectDetailResponse;
import es.princip.getp.application.project.commission.port.out.FindProjectPort;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetApplicationDetailService implements GetApplicationDetailQuery {

    private final FindProjectPort findProjectPort;
    private final LoadPeoplePort loadPeoplePort;
    private final LoadProjectApplicantPort loadApplicationPort;

    private final ProjectApplicationDetailResponseFactory responseFactory;

    @Override
    public ProjectApplicationDetailResponse getApplicationDetailBy(
        final Member member,
        final ProjectApplicationId applicationId
    ) {
        final People applicant = loadPeoplePort.loadBy(member.getId());
        final ProjectApplication application = loadApplicationPort.loadBy(applicationId);
        if (!application.isApplicant(applicant.getId())) {
            throw new NotMyProjectApplicationException();
        }
        final ProjectDetailResponse project = findProjectPort.findBy(member, application.getProjectId());
        return responseFactory.mapToResponse(application, project);
    }
}
