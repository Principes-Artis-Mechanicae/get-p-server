package es.princip.getp.domain.project.fixture;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;

import java.time.LocalDate;
import java.util.List;

public class ApplyProjectRequestFixture {

    public static ApplyProjectRequest applyProjectRequest() {
        return new ApplyProjectRequest(
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            "프로젝트 지원 설명",
            List.of(
                "https://example.com/attachment1",
                "https://example.com/attachment2"
            )
        );
    }
}