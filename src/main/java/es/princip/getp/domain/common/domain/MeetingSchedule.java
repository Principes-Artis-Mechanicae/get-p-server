package es.princip.getp.domain.common.domain;

import es.princip.getp.domain.common.exception.StartTimeIsAfterEndTimeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Embeddable
@MappedSuperclass
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSchedule {

    @Column(name = "date")
    @NotNull
    private LocalDate date;
    
    @Column(name = "start_time")
    @NotNull
    private LocalTime startTime;

    @Column(name = "end_time")
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
