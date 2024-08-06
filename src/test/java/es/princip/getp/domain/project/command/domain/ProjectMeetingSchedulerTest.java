package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.exception.NotApplicantException;
import es.princip.getp.domain.project.exception.NotClientOfProjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.phoneNumber;
import static es.princip.getp.domain.project.fixture.ProjectMeetingFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProjectMeetingSchedulerTest {

    @Mock
    private ProjectApplicationRepository applicationRepository;

    @Mock
    private ProjectMeetingRepository meetingRepository;

    @InjectMocks
    private ProjectMeetingScheduler meetingScheduler;

    private final Long memberId = 1L;
    private final Project project = mock(Project.class);
    private final People people = mock(People.class);

    @Test
    void 의뢰자는_프로젝트_지원자에게_미팅을_신청할_수_있다() {
        given(meetingRepository.existsByProjectIdAndMemberId(anyLong(), anyLong()))
            .willReturn(true);
        given(applicationRepository.existsByApplicantIdAndProjectId(anyLong(), anyLong()))
            .willReturn(true);

        final ProjectMeeting meeting = meetingScheduler.scheduleMeeting(
            memberId,
            project,
            people,
            LOCATION,
            SCHEDULE,
            phoneNumber(),
            DESCRIPTION
        );

        assertThat(meeting).isNotNull();
    }

    @Test
    void 의뢰자는_자신이_의뢰한_프로젝트가_아니면_미팅을_신청할_수_없다() {
        given(meetingRepository.existsByProjectIdAndMemberId(anyLong(), anyLong()))
            .willReturn(false);

        assertThatThrownBy(() -> meetingScheduler.scheduleMeeting(
            memberId,
            project,
            people,
            LOCATION,
            SCHEDULE,
            phoneNumber(),
            DESCRIPTION
        )).isInstanceOf(NotClientOfProjectException.class);
    }

    @Test
    void 의뢰자는_프로젝트_지원자가_아닌_피플에게_미팅을_신청할_수_없다() {
        given(meetingRepository.existsByProjectIdAndMemberId(anyLong(), anyLong()))
            .willReturn(true);
        given(applicationRepository.existsByApplicantIdAndProjectId(anyLong(), anyLong()))
            .willReturn(false);

        assertThatThrownBy(() -> meetingScheduler.scheduleMeeting(
            memberId,
            project,
            people,
            LOCATION,
            SCHEDULE,
            phoneNumber(),
            DESCRIPTION
        )).isInstanceOf(NotApplicantException.class);
    }
}