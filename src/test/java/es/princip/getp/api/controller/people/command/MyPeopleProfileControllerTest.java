package es.princip.getp.api.controller.people.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.people.command.description.request.EditPeopleProfileRequestDescription;
import es.princip.getp.api.controller.people.command.description.request.WritePeopleProfileRequestDescription;
import es.princip.getp.api.controller.people.command.dto.request.EditPeopleProfileRequest;
import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleProfileRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.people.command.EditPeopleProfileCommand;
import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;
import es.princip.getp.application.people.port.in.EditPeopleProfileUseCase;
import es.princip.getp.application.people.port.in.RegisterPeopleProfileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsRequest;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksRequest;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfoliosRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleProfileControllerTest extends ControllerTest {

    @Autowired private RegisterPeopleProfileUseCase registerPeopleProfileUseCase;
    @Autowired private EditPeopleProfileUseCase editPeopleProfileUseCase;

    private static final String MY_PEOPLE_PROFILE_URI = "/people/me/profile";

    @DisplayName("피플은 자신의 프로필을 등록할 수 있다.")
    @Nested
    class WriteMyPeopleProfile {

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

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        void writeMyPeopleProfile() throws Exception {
            willDoNothing().given(registerPeopleProfileUseCase)
                .register(any(RegisterPeopleProfileCommand.class));

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(WritePeopleProfileRequestDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        void writeMyPeopleProfile_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }

    @DisplayName("피플은 자신의 프로필을 수정할 수 있다.")
    @Nested
    class EditMyPeopleProfile {

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

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        void editMyPeopleProfile() throws Exception {
            willDoNothing().given(editPeopleProfileUseCase)
                .edit(any(EditPeopleProfileCommand.class));

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(EditPeopleProfileRequestDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        void editMyPeopleProfile_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}