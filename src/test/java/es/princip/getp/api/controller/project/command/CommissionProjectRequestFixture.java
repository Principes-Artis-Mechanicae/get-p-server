package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.fixture.common.HashtagFixture.hashtagsRequest;

class CommissionProjectRequestFixture {

    static CommissionProjectRequest registerProjectRequest() {
        return new CommissionProjectRequest(
            "프로젝트 제목",
            1_000_000L,
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            ),
            "프로젝트 설명",
            MeetingType.IN_PERSON,
            ProjectCategory.BACKEND,
            List.of(
                "https://example.com/attachment1",
                "https://example.com/attachment2"
            ),
            hashtagsRequest()
        );
    }
}
