package es.princip.getp.domain.project.query.application;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.client.exception.NotFoundClientException;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
import es.princip.getp.domain.project.exception.NotFoundProjectException;
import es.princip.getp.domain.project.exception.NotMyProjectException;
import es.princip.getp.domain.project.query.dao.ProjectApplicantDao;
import es.princip.getp.infra.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectApplicantService {

    private final ProjectApplicantDao projectApplicantDao;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;

    public PageResponse<DetailPeopleResponse> getApplicants(
        final Long projectId,
        final Member member,
        final Pageable pageable
    ) {
        final Long memberId = member.getMemberId();
        final Client client = clientRepository.findByMemberId(memberId).orElseThrow(NotFoundClientException::new);
        final Project project = projectRepository.findById(projectId).orElseThrow(NotFoundProjectException::new);
        if (!project.isClient(client)) {
            throw new NotMyProjectException();
        }
        return PageResponse.from(projectApplicantDao.findPagedApplicantByProjectId(projectId, pageable));
    }
}
