package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.project.command.domain.*;
import es.princip.getp.domain.project.command.presentation.dto.request.RegisterProjectRequest;
import es.princip.getp.domain.project.exception.ApplicationDurationIsNotBeforeEstimatedDurationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public Long register(final Long memberId, final RegisterProjectRequest request) {
        final Long clientId = clientRepository.findByMemberId(memberId).orElseThrow().getClientId();

        final ApplicationDuration applicationDuration = ApplicationDuration.from(request.applicationDuration());
        final EstimatedDuration estimatedDuration = EstimatedDuration.from(request.estimatedDuration());

        if (!applicationDuration.isBefore(estimatedDuration)) {
            throw new ApplicationDurationIsNotBeforeEstimatedDurationException();
        }

        final List<AttachmentFile> attachmentFiles = request.attachmentUris().stream()
            .map(AttachmentFile::from)
            .toList();
        final List<Hashtag> hashtags = request.hashtags().stream()
            .map(Hashtag::of)
            .toList();

        final Project project = Project.builder()
            .clientId(clientId)
            .title(request.title())
            .payment(request.payment())
            .applicationDuration(applicationDuration)
            .estimatedDuration(estimatedDuration)
            .description(request.description())
            .meetingType(request.meetingType())
            .category(request.category())
            .status(ProjectStatus.APPLYING)
            .attachmentFiles(attachmentFiles)
            .hashtags(hashtags)
            .build();

        projectRepository.save(project);

        return project.getProjectId();
    }
}
