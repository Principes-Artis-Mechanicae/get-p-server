package es.princip.getp.domain.project.command.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSchedule {
    private LocalDate meetingDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private MeetingSchedule(final LocalDate meetingDate, final LocalTime startTime, final LocalTime endTime) {
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static MeetingSchedule of (final LocalDate meetingDate, final LocalTime startTime, final LocalTime endTime) {
        return new MeetingSchedule(meetingDate, startTime, endTime);
    }

    public String formatDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return String.format("%s %s ~ %s\n\n", meetingDate.format(dateFormatter), startTime.format(timeFormatter), endTime.format(timeFormatter));
    }
}
