package es.princip.getp.api.controller.project.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.project.query.dto.ProjectApplicationDetailTeammateResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectApplicationFormResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.apply.port.in.GetApplicationFormQuery;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.apply.model.TeammateStatus;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.query.description.ProjectApplicationFormResponseDescription.projectApplicationFormResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.COMPLETED;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationType.TEAM;
import static es.princip.getp.domain.project.apply.model.TeammateStatus.APPROVED;
import static es.princip.getp.domain.project.apply.model.TeammateStatus.PENDING;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.people.ActivityAreaFixture.ACTIVITY_AREA;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.INTRODUCTION;
import static es.princip.getp.fixture.people.PortfolioFixture.portfoliosResponse;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.DESCRIPTION;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.expectedDuration;
import static es.princip.getp.fixture.project.ProjectQueryResponseFixture.projectDetailResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectApplicationDetailFormControllerTest extends ControllerTest {

    @Autowired private GetApplicationFormQuery query;

    private ProjectApplicationDetailTeammateResponse teammateResponse(
        final Long peopleId,
        final TeammateStatus status
    ) {
        return new ProjectApplicationDetailTeammateResponse(
            peopleId,
            NICKNAME + peopleId,
            status,
            profileImage(new MemberId(peopleId)).getUrl()
        );
    }

    private ProjectApplicationFormResponse applicationDetailResponse(
        final ProjectId projectId,
        final ProjectApplicationId applicationId
    ) {
        return new ProjectApplicationFormResponse(
            applicationId.getValue(),
            TEAM,
            projectDetailResponse(projectId),
            NICKNAME,
            techStacksResponse(),
            ACTIVITY_AREA,
            education(),
            INTRODUCTION,
            portfoliosResponse(),
            expectedDuration(),
            COMPLETED,
            DESCRIPTION,
            List.of(
                "https://example.com/attachment1",
                "https://example.com/attachment2"
            ),
            List.of(
                teammateResponse(2L, APPROVED),
                teammateResponse(3L, PENDING),
                teammateResponse(4L, APPROVED)
            )
        );
    }

    @Test
    @WithCustomMockUser(memberType = ROLE_CLIENT)
    void 의뢰자는_프로젝트_지원자의_지원서를_조회할_수_있다() throws Exception {
        final var projectId = new ProjectId(1L);
        final var applicationId = new ProjectApplicationId(1L);
        final var response = applicationDetailResponse(projectId, applicationId);
        given(query.getApplicationFormBy(any(Member.class), eq(applicationId)))
            .willReturn(response);

        mockMvc.perform(get("/applications/{applicationId}", applicationId.getValue())
            .header("Authorization", "Bearer ${ACCESS_TOKEN}"))
            .andExpect(status().isOk())
            .andDo(document("project/get-application-form",
                ResourceSnippetParameters.builder()
                    .tag("프로젝트 관리")
                    .description("의뢰자는 프로젝트 지원자의 지원서를 조회할 수 있다.")
                    .summary("프로젝트 지원서 조회")
                    .responseSchema(Schema.schema("ProjectApplicationFormResponse")),
                requestHeaders(authorizationHeaderDescription().optional()),
                pathParameters(parameterWithName("applicationId").description("프로젝트 지원 ID")),
                responseFields(projectApplicationFormResponseDescription())
            ))
            .andDo(print());
    }
}