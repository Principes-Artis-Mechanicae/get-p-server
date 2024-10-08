package es.princip.getp.api.controller.client.query;

import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.client.BankAccountFixture.bankAccount;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientQueryControllerTest extends ControllerTest {

    @Autowired
    private ClientQuery clientQuery;

    @AfterEach
    void tearDown() {
        Mockito.reset(clientQuery);
    }

    @Nested
    @DisplayName("의뢰자 정보 조회")
    class GetMyClient {

        final Long clientId = 1L;

        @Test
        @DisplayName("관리자는 의뢰자 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_ADMIN)
        void getClient_WhenMemberTypeIsAdmin() throws Exception {
            final LocalDateTime now = LocalDateTime.now();
            final ClientResponse response = new ClientResponse(
                clientId,
                NICKNAME,
                PHONE_NUMBER,
                EMAIL,
                profileImage(1L).getUrl(),
                address(),
                bankAccount(),
                now,
                now
            );
            given(clientQuery.findClientById(clientId)).willReturn(response);

            mockMvc.perform(get("/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("매니저는 의뢰자 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_MANAGER)
        void getClient_WhenMemberTypeIsManager() throws Exception {
            final LocalDateTime now = LocalDateTime.now();
            final ClientResponse response = new ClientResponse(
                clientId,
                NICKNAME,
                PHONE_NUMBER,
                EMAIL,
                profileImage(1L).getUrl(),
                address(),
                bankAccount(),
                now,
                now
            );
            given(clientQuery.findClientById(clientId)).willReturn(response);

            mockMvc.perform(get("/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("관리자나 매니저가 아니면 의뢰자 정보를 조회할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getClient_WhenMemberTypeIsNotAdminOrManager_Fail() throws Exception {
            mockMvc.perform(get("/client/{clientId}", clientId))
                .andExpect(status().isForbidden())
                .andDo(print());
        }
    }
}