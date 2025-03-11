package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.domain.common.model.Duration;

import java.time.LocalDate;
import java.util.List;

class ApplyProjectRequestFixture {

    static ApplyProjectRequest applyProjectRequest() {
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
