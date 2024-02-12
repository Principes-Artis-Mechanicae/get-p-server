package es.princip.getp.domain.project.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.global.exception.BusinessLogicException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("예상 작업 기간은")
public class EstiamtedDurationTest {

    @DisplayName("시작일이 종료일보다 이전이어야 한다.")
    @Test
    void startDateOfEstimatedDuration_shouldBeBeforeThenEndDate() {
        BusinessLogicException exception =
            assertThrows(BusinessLogicException.class, () -> EstimatedDuration
                .from(LocalDate.now().plusDays(14), LocalDate.now().plusDays(7)));
        assertEquals(exception.getCode(), ProjectErrorCode.INVALID_ESTIMATED_DURATION.name());
    }
}
