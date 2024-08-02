package es.princip.getp.domain.project.command.domain;

import java.util.List;

import es.princip.getp.domain.common.annotation.DomainService;
import es.princip.getp.domain.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfileChecker;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ProjectMeetingApplier {

    private final PeopleProfileChecker peopleProfileChecker;

    /**
     * 프로젝트 미팅 신청
     * 
     * @param Project 미팅이 진행되는 프로젝트
     * @param people 미팅할 피플
     * @param meetingLocation 미팅 장소
     * @param meetingSchedules 미팅 일정
     * @param contactPhoneNumber 연락처
     * @param description 요구사항
     * @return
     */
    public ProjectMeeting scheduleMeeting(
        final Project project,
        final People people,
        final String meetingLocation,
        final List<MeetingSchedule> meetingSchedules,
        final PhoneNumber contactPhoneNumber,
        final String description
    ) {
        peopleProfileChecker.checkPeopleProfileIsRegistered(people);
        return ProjectMeeting.builder()
                .projectId(project.getProjectId())
                .applicantId(people.getPeopleId())
                .meetingLocation(meetingLocation)
                .meetingSchedules(meetingSchedules)
                .phoneNumber(contactPhoneNumber)
                .description(description)
                .build();
    }
}
