package es.princip.getp.api.controller.people.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.people.command.dto.request.EditPeopleProfileRequest;
import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleProfileRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.dto.command.EditPeopleProfileCommand;
import es.princip.getp.application.people.dto.command.RegisterPeopleProfileCommand;
import es.princip.getp.application.people.port.in.EditPeopleProfileUseCase;
import es.princip.getp.application.people.port.in.RegisterPeopleProfileUseCase;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.common.fixture.HashtagDtoFixture.hashtagsRequest;
import static es.princip.getp.api.controller.common.fixture.TechStackDtoFixture.techStacksRequest;
import static es.princip.getp.api.controller.people.fixture.PortfolioFixture.portfoliosRequest;
import static es.princip.getp.api.controller.people.command.description.request.EditPeopleProfileRequestDescription.editPeopleProfileRequestDescription;
import static es.princip.getp.api.controller.people.command.description.request.RegisterPeopleProfileRequestDescription.registerPeopleProfileRequestDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleProfileControllerTest extends ControllerTest {

    @Autowired private RegisterPeopleProfileUseCase registerPeopleProfileUseCase;
    @Autowired private EditPeopleProfileUseCase editPeopleProfileUseCase;

    private static final String MY_PEOPLE_PROFILE_URI = "/people/me/profile";

    @Nested
    class 내_피플_프로필_등록 {

        private final RegisterPeopleProfileRequest request = new RegisterPeopleProfileRequest(
            education(),
            activityArea(),
            introduction(),
            techStacksRequest(),
            portfoliosRequest(),
            hashtagsRequest()
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 피플은_자신의_프로필을_등록할_수_있다() throws Exception {
            willDoNothing().given(registerPeopleProfileUseCase)
                .register(any(RegisterPeopleProfileCommand.class));

            perform()
                .andExpect(status().isCreated())
                .andDo(document("people/register-my-people-profile",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("피플은 자신의 프로필을 등록할 수 있다.")
                        .summary("내 피플 프로필 등록")
                        .requestSchema(Schema.schema("RegisterMyPeopleProfileRequest"))
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(registerPeopleProfileRequestDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 내_피플_프로필_수정 {

        private final EditPeopleProfileRequest request = new EditPeopleProfileRequest(
            education(),
            activityArea(),
            introduction(),
            techStacksRequest(),
            portfoliosRequest(),
            hashtagsRequest()
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 피플은_자신의_프로필을_수정할_수_있다() throws Exception {
            willDoNothing().given(editPeopleProfileUseCase)
                .edit(any(EditPeopleProfileCommand.class));

            perform()
                .andExpect(status().isOk())
                .andDo(document("people/edit-my-people-profile",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("피플은 자신의 프로필을 수정할 수 있다.")
                        .summary("내 피플 프로필 수정")
                        .requestSchema(Schema.schema("EditMyPeopleProfileRequest"))
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(editPeopleProfileRequestDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }
}