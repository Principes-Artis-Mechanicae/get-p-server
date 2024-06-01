package es.princip.getp.domain.client.service;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.fixture.ClientFixture;
import es.princip.getp.domain.client.repository.ClientRepository;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.dto.request.UpdateMemberRequest;
import es.princip.getp.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static es.princip.getp.domain.client.fixture.ClientFixture.createClient;
import static es.princip.getp.domain.client.fixture.ClientFixture.createClientRequest;
import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private MemberService memberService;

    @Mock
    private ClientRepository clientRepository;

    @Nested
    @DisplayName("create()는")
    class Create {

        private Member mockMember;
        private final CreateClientRequest request = createClientRequest();

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember(
                request.nickname(),
                request.phoneNumber(),
                request.profileImageUri()
            ));
            given(mockMember.getMemberId()).willReturn(1L);
        }

        @Test
        @DisplayName("의뢰자 정보를 등록한다.")
        void create() {
            Client expected = Client.from(mockMember, request);
            given(memberService.getByMemberId(mockMember.getMemberId())).willReturn(mockMember);
            given(clientRepository.save(any(Client.class))).willReturn(expected);

            Client created = clientService.create(mockMember.getMemberId(), request);

            verify(memberService).update(mockMember.getMemberId(), UpdateMemberRequest.from(request));
            assertSoftly(softly -> {
                softly.assertThat(created.getNickname()).isEqualTo(request.nickname());
                softly.assertThat(created.getEmail()).isEqualTo(request.email());
                softly.assertThat(created.getPhoneNumber()).isEqualTo(request.phoneNumber());
                softly.assertThat(created.getProfileImageUri()).isEqualTo(request.profileImageUri());
                softly.assertThat(created.getAddress()).isEqualTo(request.address());
                softly.assertThat(created.getBankAccount()).isEqualTo(request.bankAccount());
            });
        }
    }

    @Nested
    @DisplayName("getByClientId()는")
    class GetByClientId {

        private Client mockClient;

        @BeforeEach
        void setUp() {
            mockClient = Mockito.spy(createClient(createMember()));
            given(mockClient.getClientId()).willReturn(1L);
        }

        @Test
        @DisplayName("clientId로 의뢰자 정보를 조회한다.")
        void getByClientId() {
            given(clientRepository.findById(mockClient.getClientId()))
                .willReturn(Optional.of(mockClient));

            Client result = clientService.getByClientId(mockClient.getClientId());

            assertThat(mockClient).isEqualTo(result);
        }
    }

    @Nested
    @DisplayName("getByMemberId()는")
    class GetByMemberId {

        private Member mockMember;
        private Client mockClient;

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember());
            given(mockMember.getMemberId()).willReturn(1L);
            mockClient = createClient(mockMember);
        }

        @Test
        @DisplayName("memberId로 의뢰자 정보를 조회한다.")
        void testGetByMemberId() {
            given(clientRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockClient));

            Client result = clientService.getByMemberId(mockMember.getMemberId());

            assertThat(mockClient).isEqualTo(result);
        }
    }

    @Nested
    @DisplayName("update()는")
    class Update {

        private Member mockMember;
        private Client mockClient;
        private final UpdateClientRequest request = ClientFixture.updateClientRequest();

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember(
                request.nickname(),
                request.phoneNumber(),
                request.profileImageUri()
            ));
            given(mockMember.getMemberId()).willReturn(1L);
            mockClient = createClient(mockMember);
        }

        @Test
        @DisplayName("의뢰자 정보를 수정한다.")
        void update() {
            given(clientRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockClient));

            Client updated = clientService.update(mockMember.getMemberId(), request);

            verify(memberService).update(mockMember.getMemberId(), UpdateMemberRequest.from(request));
            assertSoftly(softly -> {
                softly.assertThat(updated.getNickname()).isEqualTo(request.nickname());
                softly.assertThat(updated.getEmail()).isEqualTo(request.email());
                softly.assertThat(updated.getPhoneNumber()).isEqualTo(request.phoneNumber());
                softly.assertThat(updated.getProfileImageUri()).isEqualTo(request.profileImageUri());
                softly.assertThat(updated.getAddress()).isEqualTo(request.address());
                softly.assertThat(updated.getBankAccount()).isEqualTo(request.bankAccount());
            });
        }
    }

    @Nested
    @DisplayName("delete()는")
    class Delete {

        private Member mockMember;
        private Client mockClient;

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember());
            given(mockMember.getMemberId()).willReturn(1L);
            mockClient = createClient(mockMember);
        }

        @Test
        @DisplayName("의뢰자 정보를 삭제한다.")
        void delete() {
            given(clientRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockClient));

            clientService.delete(mockMember.getMemberId());

            verify(clientRepository).delete(mockClient);
        }
    }
}