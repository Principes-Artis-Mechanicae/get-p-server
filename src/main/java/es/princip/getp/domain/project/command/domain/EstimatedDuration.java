package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.Duration;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimatedDuration extends Duration {

    private EstimatedDuration(final LocalDate startDate, final LocalDate endDate) {
        super(startDate, endDate);
    }

    public static EstimatedDuration of(final LocalDate startDate, final LocalDate endDate) {
        return new EstimatedDuration(startDate, endDate);
    }

    public static EstimatedDuration from(final Duration duration) {
        return new EstimatedDuration(duration.getStartDate(), duration.getEndDate());
    }
}
