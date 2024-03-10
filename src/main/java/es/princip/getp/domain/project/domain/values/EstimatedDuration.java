package es.princip.getp.domain.project.domain.values;

import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.global.exception.BusinessLogicException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class EstimatedDuration {

    @Column(name = "estimated_start_date")
    private LocalDate startDate;

    @Column(name = "estimated_end_date")
    private LocalDate endDate;

    private EstimatedDuration(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now()) || startDate.isAfter(endDate)) {
            throw new BusinessLogicException(ProjectErrorCode.INVALID_ESTIMATED_DURATION);
        }
    }

    public static EstimatedDuration from(LocalDate startDate, LocalDate endDate) {
        return new EstimatedDuration(startDate, endDate);
    }
}
