package es.princip.getp.domain.common.model;

import es.princip.getp.domain.BaseModel;
import es.princip.getp.domain.common.exception.StartTimeIsAfterEndTimeException;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MeetingSchedule extends BaseModel {

    @NotNull private final LocalDate date;
    @NotNull private final LocalTime startTime;
    @NotNull private final LocalTime endTime;

    public MeetingSchedule(
        final LocalDate date,
        final LocalTime startTime,
        final LocalTime endTime
    ) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

        validate();
    }

    public static MeetingSchedule of(
        final LocalDate date,
        final LocalTime startTime,
        final LocalTime endTime
    ) {
        return new MeetingSchedule(date, startTime, endTime);
    }

    @Override
    protected void validate() {
        super.validate();
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
