package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.api.controller.project.query.dto.PublicProjectDetailResponse;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.project.ProjectFixture.*;
import static es.princip.getp.fixture.project.ApplicantsCountFixture.APPLICANTS_COUNT;

class ProjectQueryResponseFixture {
    static ProjectDetailResponse projectDetailResponse(ProjectId projectId) {
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
            ProjectStatus.APPLYING,
            AttachmentFilesResponse.from(
                List.of(
                    AttachmentFile.from("https://example.com/attachment1"),
                    AttachmentFile.from("https://example.com/attachment2")
                )
            ),
            hashtagsResponse(),
            5L,
            true,
            new ProjectClientResponse(
                1L,
                NICKNAME,
                address()
            )
        );
    }

    static PublicProjectDetailResponse publicProjectDetailResponse(ProjectId projectId) {
        return new PublicProjectDetailResponse(
            projectId.getValue(),
            TITLE,
            PAYMENT,
            RECRUITMENT_COUNT,
            APPLICANTS_COUNT,
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 7)
            ),
            Duration.of(
                LocalDate.of(2024, 7, 14),
                LocalDate.of(2024, 7, 21)
            ),
            "이 정보는 로그인 후에만 접근할 수 있습니다.",
            MeetingType.IN_PERSON,
            ProjectCategory.BACKEND,
            hashtagsResponse(),
            5L
        );
    }
}
