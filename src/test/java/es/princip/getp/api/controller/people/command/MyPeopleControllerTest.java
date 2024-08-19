package es.princip.getp.api.controller.people.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.people.command.description.request.CreatePeopleRequestDescription;
import es.princip.getp.api.controller.people.command.description.request.UpdatePeopleRequestDescription;
import es.princip.getp.api.controller.people.command.description.response.CreatePeopleResponseDescription;
import es.princip.getp.api.controller.people.command.dto.request.CreatePeopleRequest;
import es.princip.getp.api.controller.people.command.dto.request.UpdatePeopleRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.domain.people.command.application.PeopleService;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.command.domain.PeopleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleControllerTest extends ControllerTest {

    @Autowired
    private PeopleService peopleService;

    @DisplayName("피플은 자신의 정보를 등록할 수 있다.")
    @Nested
    class CreateMyPeople {

        private final CreatePeopleRequest request = new CreatePeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PeopleType.INDIVIDUAL
        );
        private final Long peopleId = 1L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/people/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(request)));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void createMyPeople() throws Exception {
            given(peopleService.create(any(CreatePeopleCommand.class))).willReturn(peopleId);

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(CreatePeopleRequestDescription.description()),
                    responseFields(CreatePeopleResponseDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void createMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }

    @DisplayName("피플은 자신의 정보를 수정할 수 있다.")
    @Nested
    class UpdateMyPeople {

        private final UpdatePeopleRequest request = new UpdatePeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PeopleType.INDIVIDUAL
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put("/people/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void updateMyPeople() throws Exception {
            willDoNothing().given(peopleService).update(any(UpdatePeopleCommand.class));

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(UpdatePeopleRequestDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void updateMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}