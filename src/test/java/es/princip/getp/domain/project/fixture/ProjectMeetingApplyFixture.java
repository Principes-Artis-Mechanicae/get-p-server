package es.princip.getp.domain.project.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.princip.getp.domain.project.command.presentation.dto.MeetingSchedule;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;

public class ProjectMeetingApplyFixture {
    public static final Long PEOPLE_ID = 1L;
    public static final String PROJECT_NAME = "AI 챗봇 개발";
    public static final String MEETING_LOCATION = "디스코드";
    public static final List<MeetingSchedule> MEETING_SCHEDULES = List.of(
            MeetingSchedule.of(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1L)),
            MeetingSchedule.of(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2L)),
            MeetingSchedule.of(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(3L))
        );
    public static final String CONTACT_PHONENUMBER = "010-1234-5678";
    public static final String CONTENTS = "AI 챗봇 개발 백엔드 분야로 미팅 요청 드립니다.";

    public static ApplyProjectMeetingRequest applyProjectMeetingRequest() {
        return new ApplyProjectMeetingRequest(
            PEOPLE_ID,
            PROJECT_NAME,
            MEETING_LOCATION,
            MEETING_SCHEDULES,
            CONTACT_PHONENUMBER,
            CONTENTS
        );
    }
}
