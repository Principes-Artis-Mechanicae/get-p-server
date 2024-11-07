package es.princip.getp.api.controller.people.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.people.command.dto.request.EditPeopleRequest;
import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.dto.command.EditPeopleCommand;
import es.princip.getp.application.people.dto.command.RegisterPeopleCommand;
import es.princip.getp.application.people.port.in.EditPeopleUseCase;
import es.princip.getp.application.people.port.in.RegisterPeopleUseCase;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.people.model.PeopleId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.people.command.description.request.EditPeopleRequestDescription.editPeopleRequestDescription;
import static es.princip.getp.api.controller.people.command.description.request.RegisterPeopleRequestDescription.registerPeopleRequestDescription;
import static es.princip.getp.api.controller.people.command.description.response.RegisterPeopleResponseDescription.registerPeopleResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleControllerTest extends ControllerTest {

    @Autowired private RegisterPeopleUseCase registerPeopleUseCase;
    @Autowired private EditPeopleUseCase editPeopleUseCase;

    @Nested
    class 내_피플_정보_등록 {

        private final RegisterPeopleRequest request = new RegisterPeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER
        );
        private final PeopleId peopleId = new PeopleId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/people/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        public void 피플은_자신의_정보를_등록할_수_있다() throws Exception {
            given(registerPeopleUseCase.register(any(RegisterPeopleCommand.class))).willReturn(peopleId);

            perform()
                .andExpect(status().isCreated())
                .andDo(document("people/register-my-people",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("피플은 자신의 정보를 등록할 수 있다.")
                        .summary("내 피플 정보 등록")
                        .requestSchema(Schema.schema("RegisterMyPeopleRequest"))
                        .responseSchema(Schema.schema("RegisterMyPeopleResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(registerPeopleRequestDescription()),
                    responseFields(registerPeopleResponseDescription())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 내_피플_정보_수정 {

        private final EditPeopleRequest request = new EditPeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put("/people/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        public void 피플은_자신의_정보를_수정할_수_있다() throws Exception {
            willDoNothing().given(editPeopleUseCase).edit(any(EditPeopleCommand.class));

            perform()
                .andExpect(status().isCreated())
                .andDo(document("people/edit-my-people",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("피플은 자신의 정보를 수정할 수 있다.")
                        .summary("내 피플 정보 수정")
                        .requestSchema(Schema.schema("EditMyPeopleRequest"))
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(editPeopleRequestDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }
}