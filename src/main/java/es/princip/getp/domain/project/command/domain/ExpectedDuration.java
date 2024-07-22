package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.Duration;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ExpectedDuration extends Duration {

    public ExpectedDuration(final LocalDate startDate, final LocalDate endDate) {
        super(startDate, endDate);
    }

    public static ExpectedDuration of(final LocalDate startDate, final LocalDate endDate) {
        return new ExpectedDuration(startDate, endDate);
    }

    public static ExpectedDuration from(final Duration duration) {
        return new ExpectedDuration(duration.getStartDate(), duration.getEndDate());
    }
}
