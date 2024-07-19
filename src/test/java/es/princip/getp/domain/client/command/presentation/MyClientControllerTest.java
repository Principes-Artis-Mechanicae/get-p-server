package es.princip.getp.domain.client.command.presentation;

import es.princip.getp.domain.client.command.application.ClientService;
import es.princip.getp.domain.client.command.presentation.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.command.presentation.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.security.details.PrincipalDetails;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.client.fixture.BankAccountFixture.bankAccount;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MyClientController.class)
class MyClientControllerTest extends AbstractControllerTest {

    @MockBean
    private ClientService clientService;

    private static final String REQUEST_URI = "/client/me";

    @Nested
    @DisplayName("내 의뢰자 정보 등록")
    class Create {

        @Test
        @DisplayName("의뢰자는 의뢰자 정보를 등록할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void create(final PrincipalDetails principalDetails) throws Exception {
            final CreateClientRequest request = new CreateClientRequest(
                NICKNAME,
                EMAIL,
                PHONE_NUMBER,
                address(),
                bankAccount()
            );
            final Long memberId = principalDetails.getMember().getMemberId();
            final Long clientId = 1L;
            given(clientService.create(eq(request.toCommand(memberId)))).willReturn(clientId);

            mockMvc.perform(post(REQUEST_URI)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("내 의뢰자 정보 수정")
    class Update {

        final UpdateClientRequest request = new UpdateClientRequest(
            NICKNAME, EMAIL, PHONE_NUMBER, address(), bankAccount()
        );

        @Test
        @DisplayName("의뢰자는 자신의 의뢰자 정보를 수정할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void update(final PrincipalDetails principalDetails) throws Exception {
            final Long memberId = principalDetails.getMember().getMemberId();
            willDoNothing().given(clientService).update(eq(request.toCommand(memberId)));

            mockMvc.perform(put(REQUEST_URI)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
        }

        @Test
        @DisplayName("의뢰자는 자신의 의뢰자 정보를 등록하지 않으면 자신의 의뢰자 정보를 수정할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void update_WhenMyClientDoesNotExists_Fail(final PrincipalDetails principalDetails) throws Exception {
            final Long memberId = principalDetails.getMember().getMemberId();
            willThrow(new BusinessLogicException(ClientErrorCode.NOT_FOUND))
                .given(clientService).update(eq(request.toCommand(memberId)));

            mockMvc.perform(put(REQUEST_URI)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andDo(print());
        }
    }
}