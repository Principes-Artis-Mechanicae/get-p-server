package es.princip.getp.application.project.meeting;

import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.application.project.meeting.exception.NotApplicantException;
import es.princip.getp.application.project.meeting.exception.NotClientOfProjectException;
import es.princip.getp.application.project.meeting.port.out.CheckProjectMeetingPort;
import es.princip.getp.application.project.meeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.project.apply.ProjectApplicationRepository;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;
import es.princip.getp.fixture.people.PeopleFixture;
import es.princip.getp.fixture.project.ProjectFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static es.princip.getp.application.project.meeting.ScheduleMeetingCommandFixture.scheduleMeetingCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectMeetingServiceTest {

    @Mock private ProjectApplicationRepository applicationRepository;
    @Mock private PeopleRepository peopleRepository;
    
    @Mock private LoadProjectPort loadProjectPort;
    @Mock private SaveProjectMeetingPort saveProjectMeetingPort;
    @Mock private CheckProjectMeetingPort checkProjectMeetingPort;

    @Mock private MeetingSender meetingSender;

    @InjectMocks private ProjectMeetingService projectMeetingService;

    private final Long memberId = 1L;
    private final Long projectId = 1L;
    private final Long applicantId = 1L;
    private final Long meetingId = 1L;
    private final Project project = ProjectFixture.project(applicantId, ProjectStatus.APPLYING);
    private final People people = PeopleFixture.people(memberId, PeopleType.INDIVIDUAL);

    @BeforeEach
    void setUp() {
        given(peopleRepository.findByMemberId(memberId))
            .willReturn(Optional.of(people));
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
    }

    @Test
    void 의뢰자는_프로젝트_지원자에게_미팅을_신청할_수_있다() {
        given(checkProjectMeetingPort.existsApplicantByProjectIdAndMemberId(projectId, memberId))
            .willReturn(true);
        given(applicationRepository.existsByApplicantIdAndProjectId(applicantId, projectId))
            .willReturn(true);
        given(saveProjectMeetingPort.save(any(ProjectMeeting.class)))
            .willReturn(meetingId);
        
        final ScheduleMeetingCommand command = scheduleMeetingCommand(memberId, projectId, applicantId);

        final Long meetingId = projectMeetingService.scheduleMeeting(command);

        assertThat(meetingId).isNotNull();
        verify(meetingSender, times(1)).send(
            eq(people), eq(project), any(ProjectMeeting.class)
        );
    }

    @Test
    void 의뢰자는_자신이_의뢰한_프로젝트가_아니면_미팅을_신청할_수_없다() {
        given(checkProjectMeetingPort.existsApplicantByProjectIdAndMemberId(projectId, memberId))
            .willReturn(false);
        
        final ScheduleMeetingCommand command = scheduleMeetingCommand(memberId, projectId, applicantId);

        assertThatThrownBy(() -> projectMeetingService.scheduleMeeting(command))
            .isInstanceOf(NotClientOfProjectException.class);
    }

    @Test
    void 의뢰자는_프로젝트_지원자가_아닌_피플에게_미팅을_신청할_수_없다() {
        given(checkProjectMeetingPort.existsApplicantByProjectIdAndMemberId(projectId, memberId))
            .willReturn(true);
        given(applicationRepository.existsByApplicantIdAndProjectId(applicantId, projectId))
            .willReturn(false);
        
        final ScheduleMeetingCommand command = scheduleMeetingCommand(memberId, projectId, applicantId);
        
        assertThatThrownBy(() -> projectMeetingService.scheduleMeeting(command))
            .isInstanceOf(NotApplicantException.class);
    }
}