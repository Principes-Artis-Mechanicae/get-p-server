package es.princip.getp.application.project.meeting;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.application.project.meeting.exception.NotApplicantException;
import es.princip.getp.application.project.meeting.exception.NotClientOfProjectException;
import es.princip.getp.application.project.meeting.port.out.CheckProjectMeetingPort;
import es.princip.getp.application.project.meeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.people.model.PeopleType;
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

    @Mock private CheckProjectApplicationPort checkProjectApplicationPort;
    @Mock private LoadPeoplePort loadPeoplePort;
    
    @Mock private LoadProjectPort loadProjectPort;
    @Mock private SaveProjectMeetingPort saveProjectMeetingPort;
    @Mock private CheckProjectMeetingPort checkProjectMeetingPort;

    @Mock private MeetingSender meetingSender;

    @InjectMocks private ProjectMeetingService projectMeetingService;

    private final MemberId memberId = new MemberId(1L);
    private final Long projectId = 1L;
    private final PeopleId applicantId = new PeopleId(1L);
    private final Long meetingId = 1L;
    private final Project project = ProjectFixture.project(1L, ProjectStatus.APPLYING);
    private final People people = PeopleFixture.people(memberId, PeopleType.INDIVIDUAL);

    @BeforeEach
    void setUp() {
        given(loadPeoplePort.loadBy(applicantId)).willReturn(people);
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
    }

    @Test
    void 의뢰자는_프로젝트_지원자에게_미팅을_신청할_수_있다() {
        given(checkProjectMeetingPort.existsApplicantBy(memberId, projectId))
            .willReturn(true);
        given(checkProjectApplicationPort.existsBy(applicantId, projectId))
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
        given(checkProjectMeetingPort.existsApplicantBy(memberId, projectId))
            .willReturn(false);
        
        final ScheduleMeetingCommand command = scheduleMeetingCommand(memberId, projectId, applicantId);

        assertThatThrownBy(() -> projectMeetingService.scheduleMeeting(command))
            .isInstanceOf(NotClientOfProjectException.class);
    }

    @Test
    void 의뢰자는_프로젝트_지원자가_아닌_피플에게_미팅을_신청할_수_없다() {
        given(checkProjectMeetingPort.existsApplicantBy(memberId, projectId))
            .willReturn(true);
        given(checkProjectApplicationPort.existsBy(applicantId, projectId))
            .willReturn(false);
        
        final ScheduleMeetingCommand command = scheduleMeetingCommand(memberId, projectId, applicantId);
        
        assertThatThrownBy(() -> projectMeetingService.scheduleMeeting(command))
            .isInstanceOf(NotApplicantException.class);
    }
}