package es.princip.getp.fixture.project;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.fixture.client.AddressFixture.addressResponse;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.project.ProjectFixture.*;

public class ProjectQueryResponseFixture {

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

    public static ProjectDetailResponse projectDetailResponse(final ProjectId projectId) {
        return new ProjectDetailResponse(
            projectId.getValue(),
            TITLE,
            PAYMENT,
            RECRUITMENT_COUNT,
            5L,
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 7)
            ),
            Duration.of(
                LocalDate.of(2024, 7, 14),
                LocalDate.of(2024, 7, 21)
            ),
            DESCRIPTION,
            MeetingType.IN_PERSON,
            ProjectCategory.BACKEND,
            ProjectStatus.APPLICATION_OPENED,
            List.of(
                "https://example.com/attachment1",
                "https://example.com/attachment2"
            ),
            hashtagsResponse(),
            5L,
            true,
            new ProjectClientResponse(
                1L,
                NICKNAME,
                addressResponse()
            )
        );
    }
}
