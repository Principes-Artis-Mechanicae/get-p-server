package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleLikeServiceTest {

    @InjectMocks
    private PeopleLikeService peopleLikeService;

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private ClientRepository clientRepository;

    @Nested
    @DisplayName("like()는")
    class Like {

        private final Long peopleId = 1L;
        private final Long memberIdOfClient = 2L;
        private final Client client = spy(Client.class);

        @DisplayName("피플에게 좋아요를 누른다.")
        @Test
        void like() {
            given(peopleRepository.existsById(peopleId)).willReturn(true);
            given(clientRepository.findByMemberId(memberIdOfClient)).willReturn(Optional.of(client));

            peopleLikeService.like(memberIdOfClient, peopleId);

            verify(client, times(1)).likePeople(peopleId);
        }
    }

    @Nested
    @DisplayName("unlike()는")
    class Unlike {

        private final Long peopleId = 1L;
        private final Long memberIdOfClient = 2L;
        private final Client client = spy(Client.class);

        @DisplayName("피플에게 좋아요를 취소한다.")
        @Test
        void unlike() {
            willDoNothing().given(client).unlikePeople(peopleId);
            given(peopleRepository.existsById(peopleId)).willReturn(true);
            given(clientRepository.findByMemberId(memberIdOfClient)).willReturn(Optional.of(client));

            peopleLikeService.unlike(memberIdOfClient, peopleId);

            verify(client, times(1)).unlikePeople(peopleId);
        }
    }
}