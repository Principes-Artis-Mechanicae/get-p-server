package es.princip.getp.api.controller.client.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.client.command.dto.request.EditMyClientRequest;
import es.princip.getp.api.controller.client.command.dto.request.RegisterMyClientRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.client.port.in.EditClientUseCase;
import es.princip.getp.application.client.port.in.RegisterClientUseCase;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.client.command.description.EditMyClientRequestDescription.editMyClientRequestDescription;
import static es.princip.getp.api.controller.client.command.description.RegisterMyClientRequestDescription.registerMyClientRequestDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyClientControllerTest extends ControllerTest {

    @Autowired private RegisterClientUseCase registerClientUseCase;

    @Autowired private EditClientUseCase editClientUseCase;

    private static final String REQUEST_URI = "/client/me";

    @Nested
    class 내_의뢰자_정보_등록 {

        final RegisterMyClientRequest request = new RegisterMyClientRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            address()
        );
        final ClientId clientId = new ClientId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post(REQUEST_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_의뢰자_정보를_등록할_수_있다(PrincipalDetails principalDetails) throws Exception {
            final Member member = principalDetails.getMember();
            given(registerClientUseCase.register(eq(request.toCommand(member))))
                .willReturn(clientId);

            perform()
                .andExpect(status().isCreated())
                .andDo(document("client/register-my-client",
                    ResourceSnippetParameters.builder()
                        .tag("의뢰자")
                        .description("의뢰자는 의뢰자 정보를 등록할 수 있다.")
                        .summary("내 의뢰자 정보 등록")
                        .requestSchema(Schema.schema("RegisterMyClientRequest"))
                        .responseSchema(Schema.schema("RegisterMyClientResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(registerMyClientRequestDescription()),
                    responseFields(
                        statusField(),
                        fieldWithPath("data.clientId").description("등록된 의뢰자 ID")
                    )
                ))
                .andDo(print());
        }
    }

    @Nested
    class 내_의뢰자_정보_수정 {

        final EditMyClientRequest request = new EditMyClientRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            address()
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put(REQUEST_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_자신의_의뢰자_정보를_수정할_수_있다(final PrincipalDetails principalDetails) throws Exception {
            final MemberId memberId = principalDetails.getMember().getId();
            willDoNothing().given(editClientUseCase).edit(eq(request.toCommand(memberId)));

            perform()
                .andExpect(status().isOk())
                .andDo(document("client/edit-my-client",
                    ResourceSnippetParameters.builder()
                        .tag("의뢰자")
                        .description("의뢰자는 자신의 의뢰자 정보를 수정할 수 있다.")
                        .summary("내 의뢰자 정보 수정")
                        .requestSchema(Schema.schema("EditMyClientRequest"))
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(editMyClientRequestDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }
}