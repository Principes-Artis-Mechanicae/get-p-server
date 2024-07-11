package es.princip.getp.domain.people.application;

import es.princip.getp.domain.client.application.ClientService;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.domain.people.repository.PeopleLikeRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.domain.client.fixture.ClientFixture.createClient;
import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    }
}