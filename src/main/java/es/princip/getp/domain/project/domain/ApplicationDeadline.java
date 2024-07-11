package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
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
}
