package es.princip.getp.application.like.people.service;

import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.DeletePeopleLikePort;
import es.princip.getp.application.like.people.port.out.LoadPeopleLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.fixture.like.PeopleLikeFixture;
import es.princip.getp.fixture.people.PeopleFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.application.like.people.service.LikePeopleMockingUtil.mockMemberAlreadyLikedPeople;
import static es.princip.getp.application.like.people.service.LikePeopleMockingUtil.mockMemberNeverLikedPeople;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnlikePeopleServiceTest {

    @Mock private LoadPeoplePort loadPeoplePort;
    @Mock private LoadPeopleLikePort loadPeopleLikePort;
    @Mock private CheckPeopleLikePort checkPeopleLikePort;
    @Mock private DeletePeopleLikePort deletePeopleLikePort;
    @InjectMocks private UnlikePeopleService likePeopleService;

    private final MemberId memberId = new MemberId(1L);
    private final PeopleId peopleId = new PeopleId(1L);
    
    private final People people = PeopleFixture.people(memberId);
    private final PeopleLike like = PeopleLikeFixture.peopleLike(memberId, peopleId);

    @BeforeEach
    void setUp() {
        given(loadPeoplePort.loadBy(peopleId)).willReturn(people);
    }

    @Test
    void 의뢰자는_피플에_눌렀던_좋아요를_취소할_수_있다() {
        mockMemberAlreadyLikedPeople(checkPeopleLikePort, memberId, peopleId);
        given(loadPeopleLikePort.loadBy(memberId, peopleId))
            .willReturn(like);

        likePeopleService.unlike(memberId, peopleId);

        verify(deletePeopleLikePort, times(1)).delete(like);
    }

    @Test
    void 의뢰자는_좋아요를_누른적이_없는_피플에_좋아요를_취소할_수_없다() {
        mockMemberNeverLikedPeople(checkPeopleLikePort, memberId, peopleId);

        assertThatThrownBy(() -> likePeopleService.unlike(memberId, peopleId))
            .isInstanceOf(NeverLikedException.class);
    }
}
