package es.princip.getp.domain.project.entity;

import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.global.exception.BusinessLogicException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EstimatedDuration that = (EstimatedDuration) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
