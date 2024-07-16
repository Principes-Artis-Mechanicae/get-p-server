package es.princip.getp.domain.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@MappedSuperclass
@EqualsAndHashCode
@Getter
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
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }

    public static Duration of(LocalDate startDate, LocalDate endDate) {
        return new Duration(startDate, endDate);
    }

    public boolean isBetween(Duration duration) {
        return (startDate.isEqual(duration.startDate) || startDate.isAfter(duration.startDate)) &&
            (endDate.isEqual(duration.endDate) || endDate.isBefore(duration.endDate));
    }

    public boolean isBefore(Duration duration) {
        return endDate.isBefore(duration.startDate);
    }

    public boolean isAfter(Duration duration) {
        return startDate.isAfter(duration.endDate);
    }
}
