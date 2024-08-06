package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.like.command.domain.LikeReceivable;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.like.exception.NeverLikedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnlikePeopleServiceTest {

    @Mock
    private PeopleLikeRepository peopleLikeRepository;

    @InjectMocks
    private UnlikePeopleService unlikePeopleService;

    private final Likeable client = mock(Client.class);
    private final LikeReceivable people = mock(People.class);

    private final Long likerId = 1L;
    private final Long likedId = 1L;

    @BeforeEach
    void setUp() {
        given(client.getId()).willReturn(likerId);
        given(people.getId()).willReturn(likedId);
    }

    @Test
    void 의뢰자는_피플에_눌렀던_좋아요를_취소할_수_있다() {
        given(peopleLikeRepository.existsByLikerIdAndLikedId(likerId, likedId))
            .willReturn(true);

        unlikePeopleService.unlike(client, people);

        verify(peopleLikeRepository).deleteByLikerIdAndLikedId(likerId, likedId);
    }

    @Test
    void 의뢰자는_좋아요를_누른적이_없는_피플에_좋아요를_취소할_수_없다() {
        given(peopleLikeRepository.existsByLikerIdAndLikedId(likerId, likedId))
            .willReturn(false);

        assertThatThrownBy(() -> unlikePeopleService.unlike(client, people))
            .isInstanceOf(NeverLikedException.class);
    }
}