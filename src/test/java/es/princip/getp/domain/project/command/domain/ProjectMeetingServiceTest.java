package es.princip.getp.domain.project.command.domain;

import es.princip.getp.application.projectMeeting.MeetingSender;
import es.princip.getp.application.projectMeeting.ProjectMeetingService;
import es.princip.getp.application.projectMeeting.command.ScheduleMeetingCommand;
import es.princip.getp.application.projectMeeting.port.out.CheckProjectMeetingPort;
import es.princip.getp.application.projectMeeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.fixture.PeopleFixture;
import es.princip.getp.domain.project.exception.NotApplicantException;
import es.princip.getp.domain.project.exception.NotClientOfProjectException;
import es.princip.getp.domain.project.fixture.ProjectFixture;
import es.princip.getp.domain.project.fixture.ScheduleMeetingCommandFixture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProjectMeetingServiceTest {

    @Mock
    private ProjectApplicationRepository applicationRepository;
    
    @Mock
    private PeopleRepository peopleRepository;
    
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SaveProjectMeetingPort saveProjectMeetingPort;
    
    @Mock
    private CheckProjectMeetingPort checkProjectMeetingPort;

    @Mock
    private MeetingSender meetingSender;

    @InjectMocks
    private ProjectMeetingService projectMeetingService;

    private final Long memberId = 1L;
    private final Long projectId = 1L;
    private final Long applicantId = 1L;
    private final Long meetingId = 1L;
    private final Project project = ProjectFixture.project(applicantId, ProjectStatus.APPLYING);
    private final People people = PeopleFixture.people(memberId, PeopleType.INDIVIDUAL);
    
    @Test
    void 의뢰자는_프로젝트_지원자에게_미팅을_신청할_수_있다() {
        given(checkProjectMeetingPort.existsByProjectIdAndMemberId(anyLong(), anyLong()))
            .willReturn(true);
        given(applicationRepository.existsByApplicantIdAndProjectId(anyLong(), anyLong()))
            .willReturn(true);
        given(peopleRepository.findByMemberId(memberId))
            .willReturn(Optional.of(people));
        given(projectRepository.findById(projectId))
            .willReturn(Optional.of(project));
        given(saveProjectMeetingPort.save(any(ProjectMeeting.class)))
            .willReturn(meetingId);
        
        final ScheduleMeetingCommand command = ScheduleMeetingCommandFixture.scheduleMeetingCommand(memberId, projectId, applicantId);

        final Long meetingId = projectMeetingService.scheduleMeeting(command);

        assertThat(meetingId).isNotNull();
    }

    @Test
    void 의뢰자는_자신이_의뢰한_프로젝트가_아니면_미팅을_신청할_수_없다() {
        given(checkProjectMeetingPort.existsByProjectIdAndMemberId(anyLong(), anyLong()))
            .willReturn(false);
        given(peopleRepository.findByMemberId(anyLong()))
            .willReturn(Optional.of(people));
        given(projectRepository.findById(projectId))
            .willReturn(Optional.of(project));
        
        final ScheduleMeetingCommand command = ScheduleMeetingCommandFixture.scheduleMeetingCommand(memberId, projectId, applicantId);

        assertThatThrownBy(() -> projectMeetingService.scheduleMeeting(command))
            .isInstanceOf(NotClientOfProjectException.class);
    }

    @Test
    void 의뢰자는_프로젝트_지원자가_아닌_피플에게_미팅을_신청할_수_없다() {
        given(checkProjectMeetingPort.existsByProjectIdAndMemberId(anyLong(), anyLong()))
            .willReturn(true);
        given(applicationRepository.existsByApplicantIdAndProjectId(anyLong(), anyLong()))
            .willReturn(false);
        given(peopleRepository.findByMemberId(anyLong()))
            .willReturn(Optional.of(people));
        given(projectRepository.findById(projectId))
            .willReturn(Optional.of(project));
        
        final ScheduleMeetingCommand command = ScheduleMeetingCommandFixture.scheduleMeetingCommand(memberId, projectId, applicantId);
        
        assertThatThrownBy(() -> projectMeetingService.scheduleMeeting(command))
            .isInstanceOf(NotApplicantException.class);
    }
}