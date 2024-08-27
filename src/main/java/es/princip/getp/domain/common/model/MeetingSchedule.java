package es.princip.getp.domain.common.model;

import es.princip.getp.common.exception.StartTimeIsAfterEndTimeException;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@EqualsAndHashCode
public class MeetingSchedule {

    @NotNull
    private LocalDate date;
    
    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public MeetingSchedule(
        final LocalDate date,
        final LocalTime startTime,
        final LocalTime endTime
    ) {
        validate(startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static MeetingSchedule of(
        final LocalDate date,
        final LocalTime startTime,
        final LocalTime endTime
    ) {
        return new MeetingSchedule(date, startTime, endTime);
    }

    private void validate(final LocalTime startTime, final LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new StartTimeIsAfterEndTimeException();
        }
    }

    @Override
    public String toString() {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return String.format(
            "%s %s ~ %s",
            date.format(dateFormatter),
            startTime.format(timeFormatter),
            endTime.format(timeFormatter)
        );
    }
}
