package es.princip.getp.domain.client.query.presentation;

import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.client.query.dto.ClientResponse;
import es.princip.getp.domain.member.domain.model.MemberType;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.security.details.PrincipalDetails;
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

@WebMvcTest(controllers = MyClientQueryController.class)
class MyClientQueryControllerTest extends AbstractControllerTest {

    @MockBean
    private ClientDao clientDao;

    @Nested
    @DisplayName("내 의뢰자 정보 조회")
    class GetMyClient {

        @Test
        @DisplayName("의뢰자는 자신의 의뢰자 정보를 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getMyClient(final PrincipalDetails principalDetails) throws Exception {
            final Long memberId = principalDetails.getMember().getMemberId();
            final LocalDateTime now = LocalDateTime.now();
            final ClientResponse response = new ClientResponse(
                1L,
                NICKNAME,
                PHONE_NUMBER,
                EMAIL,
                profileImage(1L).getUri(),
                address(),
                bankAccount(),
                now,
                now
            );
            given(clientDao.findByMemberId(memberId)).willReturn(Optional.of(response));

            mockMvc.perform(get("/client/me"))
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("의뢰자는 자신의 의뢰자 정보를 등록하지 않으면 자신의 의뢰자 정보를 조회할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void getMyClient_WhenMyClientDoesNotExists_Fail(final PrincipalDetails principalDetails) throws Exception {
            final Long memberId = principalDetails.getMember().getMemberId();
            given(clientDao.findByMemberId(memberId))
                .willThrow(new BusinessLogicException(ClientErrorCode.NOT_FOUND));

            //when and then
            mockMvc.perform(get("/client/me"))
                .andExpect(status().isNotFound())
                .andDo(print());
        }
    }
}