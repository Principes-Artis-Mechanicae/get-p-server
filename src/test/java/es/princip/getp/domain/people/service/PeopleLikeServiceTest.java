package es.princip.getp.domain.people.service;

import static es.princip.getp.fixture.MemberFixture.createMember;
import static es.princip.getp.fixture.PeopleFixture.createPeople;
import static es.princip.getp.fixture.ClientFixture.createClient;
import static es.princip.getp.fixture.PeopleLikeFixture.creatPeopleLike;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleLike;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.domain.people.repository.PeopleLikeRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PeopleLikeServiceTest {

    @InjectMocks
    private PeopleLikeService peopleLikeService;

    @Mock
    private ClientService clientService;

    @Mock
    private PeopleService peopleService;

    @Mock
    private PeopleLikeRepository peopleLikeRepository;

    @Nested
    @DisplayName("like()는")
    class Like {

        private final Long memberId = 1L;
        private final Long peopleId = 1L;
        private final Member member = createMember();

        private final People people = createPeople(member);
        private final Client client = createClient();
        private final PeopleLike peopleLike = creatPeopleLike(client, people);

        @DisplayName("피플에게 좋아요를 누른다.")
        @Test
        void like() {
            given(clientService.getByMemberId(memberId)).willReturn(client);
            given(peopleService.getByPeopleId(peopleId)).willReturn(people);
            given(peopleLikeRepository.existsByClient_ClientIdAndPeople_PeopleId(
                any(), any()
            )).willReturn(false);

            peopleLikeService.like(memberId, peopleId);

            verify(peopleLikeRepository, times(1)).save(any());
        }

        @DisplayName("피플에게 이미 좋아요가 눌러진 경우 예외를 던진다.")
        @Test
        void like_WhenPeopleIsAlreadyLiked_ShouldThrowException() {
            given(clientService.getByMemberId(memberId)).willReturn(client);
            given(peopleService.getByPeopleId(peopleId)).willReturn(people);
            given(peopleLikeRepository.existsByClient_ClientIdAndPeople_PeopleId(
                any(), any()
            )).willReturn(true);

            BusinessLogicException exception =
                catchThrowableOfType(() -> peopleLikeService.like(memberId, peopleId),
                    BusinessLogicException.class);
            assertThat(exception.getErrorCode()).isEqualTo(PeopleLikeErrorCode.ALREADY_LIKED);
        }

        @DisplayName("좋아요를 누를 피플이 존재하지 않는 경우 예외를 던진다.")
        @Test
        void like_WhenPeopleIsNotFound_ShouldThrowException() {
            given(clientService.getByMemberId(memberId)).willReturn(client);
            given(peopleService.getByPeopleId(peopleId)).willThrow(
                new BusinessLogicException(PeopleErrorCode.PEOPLE_NOT_FOUND)
            );

            BusinessLogicException exception =
                catchThrowableOfType(() -> peopleLikeService.like(memberId, peopleId),
                    BusinessLogicException.class);
            assertThat(exception.getErrorCode()).isEqualTo(PeopleErrorCode.PEOPLE_NOT_FOUND);
        }

        @DisplayName("피플에게 좋아요를 취소한다.")
        @Test
        void unlike() {
            given(peopleLikeRepository.findByPeople_PeopleIdAndClient_ClientId(memberId, peopleId)).willReturn(Optional.of(peopleLike));

            peopleLikeService.unlike(memberId, peopleId);

            verify(peopleLikeRepository, times(1)).delete(peopleLike);
        }

        @DisplayName("좋아요를 누르지 않은 피플에게 의뢰자가 좋아요를 취소할 경우 예외를 던진다.")
        @Test
        void unlike_ThrowExceptionWhenLikerCancelsLikeForPeopleNotLiked() {
            given(peopleLikeRepository.findByPeople_PeopleIdAndClient_ClientId(peopleId, memberId)).willThrow(
                new BusinessLogicException(PeopleLikeErrorCode.PEOPLE_LIKE_NOT_FOUND)
            );

            BusinessLogicException exception =
                catchThrowableOfType(() -> peopleLikeService.unlike(memberId, peopleId),
                    BusinessLogicException.class);
            assertThat(exception.getErrorCode()).isEqualTo(PeopleLikeErrorCode.PEOPLE_LIKE_NOT_FOUND);
        }
    }
}