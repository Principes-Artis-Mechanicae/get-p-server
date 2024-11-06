package es.princip.getp.application.project.apply;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.project.apply.dto.response.ProjectApplicantResponse;
import es.princip.getp.application.project.apply.exception.NotMyProjectException;
import es.princip.getp.application.project.apply.port.in.GetApplicantQuery;
import es.princip.getp.application.project.apply.port.out.FindProjectApplicantPort;
import es.princip.getp.application.project.apply.port.out.SerializeApplicantCursorPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.application.support.dto.SliceResponse;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetApplicantService implements GetApplicantQuery {

    private final LoadClientPort loadClientPort;
    private final LoadProjectPort loadProjectPort;

    private final FindProjectApplicantPort findApplicantPort;
    private final SerializeApplicantCursorPort serializeApplicantCursorPort;

    @Override
    public SliceResponse<ProjectApplicantResponse> getApplicantsBy(
        final CursorPageable<? extends Cursor> pageable,
        final Member member,
        final ProjectId projectId
    ) {
        final Client client = loadClientPort.loadBy(member.getId());
        final Project project = loadProjectPort.loadBy(projectId);
        if (!project.isClient(client)) {
            throw new NotMyProjectException();
        }
        final Slice<ProjectApplicantResponse> response = findApplicantPort.findApplicantsBy(pageable, projectId);
        final String cursor = serializeApplicantCursorPort.serializeCursor(response);
        return SliceResponse.of(response, cursor);
    }
}