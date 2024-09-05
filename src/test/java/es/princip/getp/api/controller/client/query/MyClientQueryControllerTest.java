package es.princip.getp.api.controller.client.query;

import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.api.controller.client.query.description.ClientResponseDescription;
import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.client.BankAccountFixture.bankAccount;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyClientQueryControllerTest extends ControllerTest {

    @Autowired
    private ClientQuery clientQuery;

    @Nested
    @DisplayName("내 의뢰자 정보 조회")
    class GetMyClient {

        final Long memberId = 1L;
        final LocalDateTime now = LocalDateTime.now();
        final ClientResponse response = new ClientResponse(
            1L,
            NICKNAME,
            PHONE_NUMBER,
            EMAIL,
            profileImage(memberId).getUrl(),
            address(),
            bankAccount(),
            now,
            now
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/client/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @DisplayName("의뢰자는 자신의 의뢰자 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getMyClient() throws Exception {
            given(clientQuery.findClientByMemberId(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        PayloadDocumentationHelper.responseFields(ClientResponseDescription.description())
                    )
                )
                .andDo(print());
        }
    }
}