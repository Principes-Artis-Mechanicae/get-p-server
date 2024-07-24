package es.princip.getp.domain.common.domain;

import es.princip.getp.domain.common.exception.StartDateIsAfterEndDateException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Embeddable
@MappedSuperclass
@EqualsAndHashCode
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Duration {

    @Column(name = "application_start_date")
    protected LocalDate startDate;

    @Column(name = "application_end_date")
    protected LocalDate endDate;

    protected Duration(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateIsAfterEndDateException();
        }
    }

    public static Duration of(LocalDate startDate, LocalDate endDate) {
        return new Duration(startDate, endDate);
    }

    /**
     * 주어진 기간의 시작일보다 종료일이 느린지 비교한다.
     *
     * @param duration 비교할 기간
     * @return 종료일이 느리면 true, 그렇지 않으면 false
     */
    public boolean isBefore(Duration duration) {
        return endDate.isBefore(duration.startDate);
    }

    /**
     * 주어진 기간의 종료일보다 시작일이 빠른지 비교한다.
     *
     * @param duration 비교할 기간
     * @return 시작일이 빠르면 true, 그렇지 않으면 false
     */
    public boolean isAfter(Duration duration) {
        return startDate.isAfter(duration.endDate);
    }

    /**
     * 시작일과 종료일의 차이를 구한다.
     *
     * @return 시작일과 종료일의 차이 일수
     */
    public Long days() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
