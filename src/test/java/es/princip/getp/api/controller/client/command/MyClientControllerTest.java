package es.princip.getp.api.controller.client.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.client.command.description.EditMyClientRequestDescription;
import es.princip.getp.api.controller.client.command.description.RegisterMyClientRequestDescription;
import es.princip.getp.api.controller.client.command.dto.request.EditMyClientRequest;
import es.princip.getp.api.controller.client.command.dto.request.RegisterMyClientRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.client.command.application.ClientService;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.client.fixture.BankAccountFixture.bankAccount;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyClientControllerTest extends ControllerTest {

    @Autowired
    private ClientService clientService;

    private static final String REQUEST_URI = "/client/me";

    @Nested
    @DisplayName("내 의뢰자 정보 등록")
    class RegisterMyClient {

        final RegisterMyClientRequest request = new RegisterMyClientRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            address(),
            bankAccount()
        );
        final Long clientId = 1L;
        final Long memberId = 1L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post(REQUEST_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @DisplayName("의뢰자는 의뢰자 정보를 등록할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void registerMyClient() throws Exception {
            given(clientService.registerClient(eq(request.toCommand(memberId))))
                .willReturn(clientId);

            perform()
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        PayloadDocumentation.requestFields(RegisterMyClientRequestDescription.description()),
                        responseFields(
                           getDescriptor("clientId", "등록된 의뢰자 ID")
                        )
                    )
                )
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("내 의뢰자 정보 수정")
    class EditMyClient {

        final EditMyClientRequest request = new EditMyClientRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            address(),
            bankAccount()
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put(REQUEST_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @DisplayName("의뢰자는 자신의 의뢰자 정보를 수정할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void editMyClient(final PrincipalDetails principalDetails) throws Exception {
            final Long memberId = principalDetails.getMember().getMemberId();
            willDoNothing().given(clientService).editClient(eq(request.toCommand(memberId)));

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        PayloadDocumentation.requestFields(EditMyClientRequestDescription.description())
                    )
                )
                .andDo(print());
        }
    }
}