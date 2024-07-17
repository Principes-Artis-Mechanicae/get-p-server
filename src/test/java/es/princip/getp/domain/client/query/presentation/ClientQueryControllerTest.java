package es.princip.getp.domain.client.query.presentation;

import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.client.query.dto.ClientResponse;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.client.fixture.BankAccountFixture.bankAccount;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientQueryController.class)
class ClientQueryControllerTest extends AbstractControllerTest {

    @MockBean
    private ClientDao clientDao;

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
                profileImage(1L).getUri(),
                address(),
                bankAccount(),
                now,
                now
            );
            given(clientDao.findById(clientId)).willReturn(Optional.of(response));

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
                profileImage(1L).getUri(),
                address(),
                bankAccount(),
                now,
                now
            );
            given(clientDao.findById(clientId)).willReturn(Optional.of(response));

            mockMvc.perform(get("/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("관리자나 매니저가 아니면 의뢰자 정보를 조회할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getClient_WhenMemberTypeIsNotAdminOrManager_Fail() throws Exception {
            given(clientDao.findById(clientId)).willThrow(BusinessLogicException.class);

            mockMvc.perform(get("/client/{clientId}", clientId))
                .andExpect(status().isForbidden())
                .andDo(print());
        }
    }
}