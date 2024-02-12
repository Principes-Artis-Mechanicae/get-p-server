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
public class ApplicationDeadline {

    @Column(name = "application_deadline")
    private LocalDate value;

    private ApplicationDeadline(LocalDate value, EstimatedDuration estimatedDuration) {
        validate(value, estimatedDuration);
        this.value = value;
    }

    private void validate(LocalDate value, EstimatedDuration estimatedDuration) {
        // 지원자 모집 마감일은 오늘 이후여야 하고, 예상 작업 시작일 이전이어야 한다.
        if (value.isBefore(LocalDate.now()) || value.isAfter(estimatedDuration.getStartDate())) {
            throw new BusinessLogicException(ProjectErrorCode.INVALID_APPLICATION_DEADLINE);
        }
    }

    public static ApplicationDeadline from(LocalDate value, EstimatedDuration estimatedDuration) {
        return new ApplicationDeadline(value, estimatedDuration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationDeadline that = (ApplicationDeadline) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
