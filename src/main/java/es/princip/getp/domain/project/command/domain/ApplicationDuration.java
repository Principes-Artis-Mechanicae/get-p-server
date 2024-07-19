package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.Duration;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationDuration extends Duration {

    private ApplicationDuration(final LocalDate startDate, final LocalDate endDate) {
        super(startDate, endDate);
    }

    public static ApplicationDuration of(final LocalDate startDate, final LocalDate endDate) {
        return new ApplicationDuration(startDate, endDate);
    }

    public static ApplicationDuration from(final Duration duration) {
        return new ApplicationDuration(duration.getStartDate(), duration.getEndDate());
    }

    public boolean isClosed() {
        return endDate.isBefore(LocalDate.now());
    }
}
