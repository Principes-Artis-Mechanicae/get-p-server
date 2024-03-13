package es.princip.getp.domain.project.domain.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.global.exception.BusinessLogicException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지원자 모집 마감일은")
public class ApplicationDeadlineTest {

    @DisplayName("지금보다 이후이어야 한다.")
    @Test
    void applicationDeadline_shouldBeAfterThenNow() {
        EstimatedDuration estimatedDuration =
            EstimatedDuration.from(LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
        BusinessLogicException exception = assertThrows(BusinessLogicException.class,
            () -> ApplicationDeadline.from(LocalDate.now().minusDays(7), estimatedDuration));
        assertEquals(exception.getErrorCode(), ProjectErrorCode.INVALID_APPLICATION_DEADLINE);
    }

    @DisplayName("예상 작업 기간보다 이전이어야 한다.")
    @Test
    void applicationDeadline_shouldBeBeforeThenEstimatedDuration() {
        EstimatedDuration estimatedDuration =
            EstimatedDuration.from(LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
        BusinessLogicException exception = assertThrows(BusinessLogicException.class,
            () -> ApplicationDeadline.from(LocalDate.now().plusDays(21), estimatedDuration));
        assertEquals(exception.getErrorCode(), ProjectErrorCode.INVALID_APPLICATION_DEADLINE);
    }
}
