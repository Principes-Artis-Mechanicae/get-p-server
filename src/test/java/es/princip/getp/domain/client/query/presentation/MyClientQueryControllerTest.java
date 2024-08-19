package es.princip.getp.domain.client.query.presentation;

import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.client.query.dto.ClientResponse;
import es.princip.getp.domain.client.query.presentation.description.ClientResponseDescription;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.client.fixture.BankAccountFixture.bankAccount;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyClientQueryControllerTest extends ControllerTest {

    @Autowired
    private ClientDao clientDao;

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
            profileImage(memberId).getUri(),
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
            given(clientDao.findByMemberId(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        responseFields(ClientResponseDescription.description())
                    )
                )
                .andDo(print());
        }
    }
}