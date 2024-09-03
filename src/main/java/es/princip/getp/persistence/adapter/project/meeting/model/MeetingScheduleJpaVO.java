package es.princip.getp.persistence.adapter.project.meeting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingScheduleJpaVO {

    @Column(name = "meeting_date")
    private LocalDate date;
    
    @Column(name = "meeting_start_time")
    private LocalTime startTime;

    @Column(name = "meeting_end_time")
    private LocalTime endTime;
}
