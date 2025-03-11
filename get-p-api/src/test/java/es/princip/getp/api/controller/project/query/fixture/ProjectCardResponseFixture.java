package es.princip.getp.api.controller.project.query.fixture;

import es.princip.getp.application.project.commission.dto.response.ProjectCardResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

import java.time.LocalDate;

import static es.princip.getp.api.controller.common.fixture.HashtagDtoFixture.hashtagsResponse;
import static es.princip.getp.fixture.project.ProjectFixture.*;

public class ProjectCardResponseFixture {

    public static ProjectCardResponse projectCardResponse(final ProjectId projectId) {
        return new ProjectCardResponse(
            projectId.getValue(),
            TITLE,
            PAYMENT,
            RECRUITMENT_COUNT,
            5L,
            10L,
            Duration.of(
                    LocalDate.of(2024, 7, 1),
                    LocalDate.of(2024, 7, 7)
            ),
            hashtagsResponse(),
            DESCRIPTION,
            ProjectStatus.APPLICATION_OPENED
        );
    }
}
