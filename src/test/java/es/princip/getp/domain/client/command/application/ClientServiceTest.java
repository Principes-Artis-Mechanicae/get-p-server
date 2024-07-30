package es.princip.getp.domain.client.command.application;

import es.princip.getp.domain.client.command.application.command.EditClientCommand;
import es.princip.getp.domain.client.command.application.command.RegisterClientCommand;
import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.application.command.UpdateMemberCommand;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.client.fixture.BankAccountFixture.bankAccount;
import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.NicknameFixture.nickname;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.phoneNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ClientRepository clientRepository;

    @Nested
    @DisplayName("registerClient()는")
    class RegisterClient {

        private final Long clientId = 1L;
        private final Long memberId = 1L;

        @Test
        @DisplayName("의뢰자 정보를 등록한다.")
        void registerClient() {
            final RegisterClientCommand command = new RegisterClientCommand(
                memberId,
                nickname(),
                email(),
                phoneNumber(),
                address(),
                bankAccount()
            );
            final Client client = spy(Client.class);

            given(client.getClientId()).willReturn(clientId);
            willDoNothing().given(memberService).update(eq(UpdateMemberCommand.from(command)));
            given(clientRepository.save(any(Client.class))).willReturn(client);

            final Long clientId = clientService.registerClient(command);

            assertThat(clientId).isEqualTo(this.clientId);
        }

        @Test
        @DisplayName("이메일이 입력되지 않은 경우 회원 정보의 이메일 주소를 사용한다.")
        void registerClient_WhenEmailIsNull_UseEmailOfMember() {
            final RegisterClientCommand command = new RegisterClientCommand(
                memberId,
                nickname(),
                null,
                phoneNumber(),
                address(),
                bankAccount()
            );
            final Client client = spy(Client.class);
            final Member member = spy(Member.class);

            given(client.getClientId()).willReturn(clientId);
            given(member.getEmail()).willReturn(email());
            willDoNothing().given(memberService).update(eq(UpdateMemberCommand.from(command)));
            given(memberRepository.findById(command.memberId())).willReturn(Optional.of(member));
            given(clientRepository.save(any(Client.class))).willReturn(client);

            final Long clientId = clientService.registerClient(command);

            assertThat(clientId).isEqualTo(this.clientId);
        }
    }

    @Nested
    @DisplayName("editClient()는")
    class EditClient {

        private final Long memberId = 1L;

        @Test
        @DisplayName("의뢰자 정보를 수정한다.")
        void editClient() {
            final EditClientCommand command = new EditClientCommand(
                memberId,
                nickname(),
                email(),
                phoneNumber(),
                address(),
                bankAccount()
            );
            final Client client = spy(Client.class);
            given(clientRepository.findByMemberId(command.memberId())).willReturn(Optional.of(client));

            clientService.editClient(command);

            verify(client, times(1)).edit(command.email(), command.address(), command.bankAccount());
        }
    }

    @Nested
    @DisplayName("delete()는")
    class Delete {

        final Long memberId = 1L;

        @Test
        @DisplayName("의뢰자 정보를 삭제한다.")
        void delete() {
            final Client client = spy(Client.class);
            given(clientRepository.findByMemberId(memberId)).willReturn(Optional.of(client));

            clientService.delete(memberId);

            verify(clientRepository, times(1)).delete(client);
        }
    }
}