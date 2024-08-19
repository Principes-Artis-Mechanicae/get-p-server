package es.princip.getp.domain.project.command.domain;

import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.model.PhoneNumber;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.exception.NotApplicantException;
import es.princip.getp.domain.project.exception.NotClientOfProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMeetingScheduler {

    private final ProjectApplicationRepository applicationRepository;
    private final ProjectMeetingRepository meetingRepository;

    /**
     * 프로젝트 미팅 신청
     *
     * @param memberId 미팅이 진행되는 프로젝트의 의뢰자의 회원 ID
     * @param project 미팅이 진행되는 프로젝트
     * @param people 미팅할 피플
     * @param location 미팅 장소
     * @param schedule 미팅 일정
     * @param phoneNumber 연락처
     * @param description 요구사항
     * @return 미팅 정보
     */
    public ProjectMeeting scheduleMeeting(
        final Long memberId,
        final Project project,
        final People people,
        final String location,
        final MeetingSchedule schedule,
        final PhoneNumber phoneNumber,
        final String description
    ) {
        final Long projectId = project.getProjectId();
        final Long applicantId = people.getPeopleId();

        checkMemberIsClientOfProject(memberId, projectId);
        checkPeopleIsApplicant(applicantId, projectId);

        return ProjectMeeting.builder()
                .projectId(project.getProjectId())
                .applicantId(people.getPeopleId())
                .location(location)
                .schedule(schedule)
                .phoneNumber(phoneNumber)
                .description(description)
                .build();
    }

    private void checkPeopleIsApplicant(final Long applicantId, final Long projectId) {
        if (!applicationRepository.existsByApplicantIdAndProjectId(applicantId, projectId)) {
            throw new NotApplicantException();
        }
    }

    private void checkMemberIsClientOfProject(final Long memberId, final Long projectId) {
        if (!meetingRepository.existsByProjectIdAndMemberId(projectId, memberId)) {
            throw new NotClientOfProjectException();
        }
    }
}
