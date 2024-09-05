package es.princip.getp.application.like;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.port.out.people.CheckPeopleLikePort;
import es.princip.getp.application.like.port.out.people.LoadPeopleLikePort;
import es.princip.getp.application.like.port.out.people.PeopleLikePort;
import es.princip.getp.application.like.port.out.people.PeopleUnlikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.like.model.people.PeopleLike;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.fixture.client.ClientFixture;
import es.princip.getp.fixture.like.PeopleLikeFixture;
import es.princip.getp.fixture.people.PeopleFixture;

@ExtendWith(MockitoExtension.class)
class PeopleLikeServiceTest {

    @Mock private PeopleLikePort peopleLikePort;

    @Mock private PeopleUnlikePort peopleUnlikePort;

    @Mock private LoadPeopleLikePort loadPeopleLikePort;

    @Mock private CheckPeopleLikePort checkPeopleLikePort;

    @Mock private LoadPeoplePort loadPeoplePort;

    @Mock private LoadClientPort loadClientPort;

    @InjectMocks private PeopleLikeService peopleLikeService;

    private final Long clientId = 1L;
    private final Long memberId = 1L;
    private final Long peopleId = 1L;
    
    private final People people = PeopleFixture.people(memberId, PeopleType.INDIVIDUAL);    
    private final PeopleLike like = PeopleLikeFixture.peopleLike(clientId, peopleId);

    @BeforeEach
    void setUp() {
        given(loadPeoplePort.loadByPeopleId(peopleId)).willReturn(people);
        Client client = spy(ClientFixture.client(clientId));
        doReturn(clientId).when(client).getClientId();
        given(loadClientPort.loadBy(memberId)).willReturn(client);
    }

    @Test
    void 의뢰자는_피플에_좋아요를_누를_수_있다() {
        given(checkPeopleLikePort.existsByClientIdAndPeopleId(clientId, peopleId))
            .willReturn(false);
        
        peopleLikeService.like(memberId, peopleId);

        verify(peopleLikePort, times(1)).like(
            argThat(
                arg-> arg.getClientId().equals(clientId) && arg.getPeopleId().equals(peopleId)
            )
        );
    }

    @Test
    void 의뢰자는_피플에_좋아요를_중복으로_누를_수_없다() {
        given(checkPeopleLikePort.existsByClientIdAndPeopleId(clientId, peopleId))
            .willReturn(true);

        assertThatThrownBy(() -> peopleLikeService.like(memberId, peopleId))
            .isInstanceOf(AlreadyLikedException.class);
    }

    @Test
    void 의뢰자는_피플에_눌렀던_좋아요를_취소할_수_있다() {
        given(checkPeopleLikePort.existsByClientIdAndPeopleId(clientId, peopleId))
            .willReturn(true);
        given(loadPeopleLikePort.findByClientIdAndPeopleId(clientId, peopleId))
            .willReturn(like);

        
        peopleLikeService.unlike(memberId, peopleId);

        verify(peopleUnlikePort, times(1)).unlike(like);
    }

    @Test
    void 의뢰자는_좋아요를_누른적이_없는_피플에_좋아요를_취소할_수_없다() {
        given(checkPeopleLikePort.existsByClientIdAndPeopleId(clientId, peopleId))
            .willReturn(false);

        assertThatThrownBy(() -> peopleLikeService.unlike(memberId, peopleId))
            .isInstanceOf(NeverLikedException.class);
    }
}
