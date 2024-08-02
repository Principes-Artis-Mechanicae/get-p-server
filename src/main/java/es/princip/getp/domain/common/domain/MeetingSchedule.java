package es.princip.getp.domain.common.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import es.princip.getp.domain.common.exception.StartTimeIsAfterEndTimeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSchedule {

    @Column(name = "meeting_date")
    private LocalDate meetingDate;
    
    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public MeetingSchedule(final LocalDate meetingDate, final LocalTime startTime, final LocalTime endTime) {
        validate(startTime, endTime);
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static MeetingSchedule of (final LocalDate meetingDate, final LocalTime startTime, final LocalTime endTime) {
        return new MeetingSchedule(meetingDate, startTime, endTime);
    }

    private void validate(final LocalTime startTime, final LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new StartTimeIsAfterEndTimeException();
        }
    }

    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return String.format("%s %s ~ %s\n\n", meetingDate.format(dateFormatter), startTime.format(timeFormatter), endTime.format(timeFormatter));
    }
}
