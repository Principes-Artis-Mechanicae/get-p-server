package es.princip.getp.api.controller.project.command.fixture;

import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.api.controller.common.fixture.HashtagDtoFixture.hashtagsRequest;
import static es.princip.getp.fixture.project.ProjectFixture.*;

public class CommissionProjectRequestFixture {

    public static CommissionProjectRequest commissionProjectRequest() {
        return new CommissionProjectRequest(
            TITLE,
            PAYMENT,
            RECRUITMENT_COUNT,
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            ),
            DESCRIPTION,
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
