package es.princip.getp.domain.client.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.client.repository.ClientRepository;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.fixture.ClientFixture;
import es.princip.getp.fixture.MemberFixture;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private final Member testMember = MemberFixture.createMember();
    private final CreateClientRequest testCreateClientRequest = ClientFixture.createClientRequest();
    private final Client testClient = ClientFixture.createClient(testMember);

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Nested
    @DisplayName("Create()는")
    class create {
        @Test
        @DisplayName("Create를 성공한다.")
        void testCreate() {
            when(clientRepository.save(any(Client.class))).thenReturn(testClient);

            Client createdClient = clientService.create(testMember, testCreateClientRequest);
            
            assertSoftly(softly -> {
                softly.assertThat(testClient.getClientId()).isEqualTo(createdClient.getClientId());
                softly.assertThat(testClient.getNickname()).isEqualTo(createdClient.getNickname());
                softly.assertThat(testClient.getEmail()).isEqualTo(createdClient.getEmail());
                softly.assertThat(testClient.getPhoneNumber()).isEqualTo(createdClient.getPhoneNumber());
                softly.assertThat(testClient.getProfileImageUri()).isEqualTo(createdClient.getProfileImageUri());
                softly.assertThat(testClient.getAddress()).isEqualTo(createdClient.getAddress());
                softly.assertThat(testClient.getAccountNumber()).isEqualTo(createdClient.getAccountNumber());
                softly.assertThat(testClient.getMember()).isEqualTo(createdClient.getMember());
            });
        }
    }

    @Nested
    @DisplayName("Read()는")
    class read {
        @Test
        @DisplayName("ClientId로 조회를 성공한다.")
        void testGetByClientId() {
            when(clientRepository.findById(testClient.getClientId())).thenReturn(Optional.of(testClient));

            Client result = clientService.getByClientId(testClient.getClientId());

            assertThat(testClient).isEqualTo(result);
        }

        @Test
        @DisplayName("MemberId로 조회를 성공한다.")
        void testGetByMemberId() {
            when(clientRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.of(testClient));

            Client result = clientService.getByMemberId(testMember.getMemberId());

            assertThat(testClient).isEqualTo(result);
        }
    }

    @Nested
    @DisplayName("Update()는")
    class update {
        private final UpdateClientRequest testUpdateClientRequest = ClientFixture.updateClientRequest();
        @Test
        @DisplayName("Update를 성공한다.")
        void testUpdate() {
            when(clientRepository.save(any(Client.class))).thenReturn(any(Client.class));
            clientService.create(testMember, testCreateClientRequest);
            when(clientRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.of(testClient));

            Client updatedClient = clientService.update(testMember.getMemberId(), testUpdateClientRequest);

            assertSoftly(softly -> {
                softly.assertThat(testClient.getClientId()).isEqualTo(updatedClient.getClientId());
                softly.assertThat(testClient.getNickname()).isEqualTo(updatedClient.getNickname());
                softly.assertThat(testClient.getEmail()).isEqualTo(updatedClient.getEmail());
                softly.assertThat(testClient.getPhoneNumber()).isEqualTo(updatedClient.getPhoneNumber());
                softly.assertThat(testClient.getProfileImageUri()).isEqualTo(updatedClient.getProfileImageUri());
                softly.assertThat(testClient.getAddress()).isEqualTo(updatedClient.getAddress());
                softly.assertThat(testClient.getAccountNumber()).isEqualTo(updatedClient.getAccountNumber());
                softly.assertThat(testClient.getMember()).isEqualTo(updatedClient.getMember());
            });
        }
    }

    @Nested
    @DisplayName("Delete()는")
    class delete {
        @Test
        @DisplayName("Delete를 성공한다.")
        void testDelete() {
            when(clientRepository.save(any(Client.class))).thenReturn(any(Client.class));
            clientService.create(testMember, testCreateClientRequest);
            when(clientRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.of(testClient));
            doNothing().when(clientRepository).delete(testClient);
        
            clientService.delete(testMember.getMemberId());

            verify(clientRepository).delete(testClient);
        }
    }
}