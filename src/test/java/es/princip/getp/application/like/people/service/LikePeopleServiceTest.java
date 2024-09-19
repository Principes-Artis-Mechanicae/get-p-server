package es.princip.getp.application.like.people.service;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.SavePeopleLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.people.model.PeopleType;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikePeopleServiceTest {

    @Mock private LoadPeoplePort loadPeoplePort;
    @Mock private SavePeopleLikePort savePeopleLikePort;
    @Mock private CheckPeopleLikePort checkPeopleLikePort;
    @InjectMocks private LikePeopleService likePeopleService;

    private final MemberId memberId = new MemberId(1L);
    private final PeopleId peopleId = new PeopleId(1L);
    
    private final People people = PeopleFixture.people(memberId, PeopleType.INDIVIDUAL);

    @BeforeEach
    void setUp() {
        given(loadPeoplePort.loadBy(peopleId)).willReturn(people);
    }

    @Test
    void 의뢰자는_피플에_좋아요를_누를_수_있다() {
        mockMemberNeverLikedPeople(checkPeopleLikePort, memberId, peopleId);
        
        likePeopleService.like(memberId, peopleId);

        verify(savePeopleLikePort, times(1)).save(
            argThat(
                arg-> arg.getMemberId().equals(memberId) && arg.getPeopleId().equals(peopleId)
            )
        );
    }

    @Test
    void 의뢰자는_피플에_좋아요를_중복으로_누를_수_없다() {
        mockMemberAlreadyLikedPeople(checkPeopleLikePort, memberId, peopleId);

        assertThatThrownBy(() -> likePeopleService.like(memberId, peopleId))
            .isInstanceOf(AlreadyLikedException.class);
    }
}
