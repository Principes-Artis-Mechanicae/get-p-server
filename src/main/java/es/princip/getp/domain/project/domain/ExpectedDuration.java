package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ExpectedDuration {

    @Column(name = "expected_start_date")
    private LocalDate startDate;

    @Column(name = "expected_end_date")
    private LocalDate endDate;

    private ExpectedDuration(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now()) || startDate.isAfter(endDate)) {
            throw new BusinessLogicException(ProjectErrorCode.INVALID_ESTIMATED_DURATION);
        }
    }

    public static ExpectedDuration from(LocalDate startDate, LocalDate endDate) {
        return new ExpectedDuration(startDate, endDate);
    }
}
