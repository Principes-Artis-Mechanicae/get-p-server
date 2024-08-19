package es.princip.getp.domain.project.fixture;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;
import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.common.fixture.HashtagFixture.hashtagsRequest;

public class CommissionProjectRequestFixture {

    public static CommissionProjectRequest registerProjectRequest() {
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
