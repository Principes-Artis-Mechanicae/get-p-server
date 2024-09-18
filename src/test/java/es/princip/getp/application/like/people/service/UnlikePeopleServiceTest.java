package es.princip.getp.application.like.people.service;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.DeletePeopleLikePort;
import es.princip.getp.application.like.people.port.out.LoadPeopleLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.fixture.client.ClientFixture;
import es.princip.getp.fixture.like.PeopleLikeFixture;
import es.princip.getp.fixture.people.PeopleFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnlikePeopleServiceTest {

    @Mock private LoadPeoplePort loadPeoplePort;
    @Mock private LoadClientPort loadClientPort;
    @Mock private LoadPeopleLikePort loadPeopleLikePort;
    @Mock private CheckPeopleLikePort checkPeopleLikePort;
    @Mock private DeletePeopleLikePort deletePeopleLikePort;
    @InjectMocks private UnlikePeopleService likePeopleService;

    private final ClientId clientId = new ClientId(1L);
    private final MemberId memberId = new MemberId(1L);
    private final PeopleId peopleId = new PeopleId(1L);
    
    private final People people = PeopleFixture.people(memberId, PeopleType.INDIVIDUAL);    
    private final PeopleLike like = PeopleLikeFixture.peopleLike(clientId, peopleId);

    @BeforeEach
    void setUp() {
        given(loadPeoplePort.loadBy(peopleId)).willReturn(people);
        Client client = spy(ClientFixture.client(memberId));
        doReturn(clientId).when(client).getId();
        given(loadClientPort.loadBy(memberId)).willReturn(client);
    }

    @Test
    void 의뢰자는_피플에_눌렀던_좋아요를_취소할_수_있다() {
        given(checkPeopleLikePort.existsBy(clientId, peopleId))
            .willReturn(true);
        given(loadPeopleLikePort.loadBy(clientId, peopleId))
            .willReturn(like);

        
        likePeopleService.unlike(memberId, peopleId);

        verify(deletePeopleLikePort, times(1)).delete(like);
    }

    @Test
    void 의뢰자는_좋아요를_누른적이_없는_피플에_좋아요를_취소할_수_없다() {
        given(checkPeopleLikePort.existsBy(clientId, peopleId))
            .willReturn(false);

        assertThatThrownBy(() -> likePeopleService.unlike(memberId, peopleId))
            .isInstanceOf(NeverLikedException.class);
    }
}
