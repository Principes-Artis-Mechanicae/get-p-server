package es.princip.getp.domain.people.command.presentation;

import es.princip.getp.domain.people.command.application.PeopleProfileService;
import es.princip.getp.domain.people.command.application.command.EditPeopleProfileCommand;
import es.princip.getp.domain.people.command.application.command.RegisterPeopleProfileCommand;
import es.princip.getp.domain.people.command.presentation.description.request.EditPeopleProfileRequestDescription;
import es.princip.getp.domain.people.command.presentation.description.request.WritePeopleProfileRequestDescription;
import es.princip.getp.domain.people.command.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.RegisterPeopleProfileRequest;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtagsRequest;
import static es.princip.getp.domain.common.fixture.TechStackFixture.techStacksRequest;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfoliosRequest;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MyPeopleProfileController.class})
class MyPeopleProfileControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleCommandMapper peopleCommandMapper;

    @MockBean
    private PeopleProfileService peopleProfileService;

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
            willDoNothing().given(peopleProfileService)
                .registerProfile(any(RegisterPeopleProfileCommand.class));

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
            willDoNothing().given(peopleProfileService)
                .editProfile(any(EditPeopleProfileCommand.class));

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