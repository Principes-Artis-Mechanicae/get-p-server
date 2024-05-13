package es.princip.getp.domain.people.service;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.fixture.PeopleFixture;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    private final Member mockMember = MemberFixture.createMember();
    private final People mockPeople = PeopleFixture.createPeople(mockMember);

    @InjectMocks
    private PeopleService peopleService;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("create()는")
    class Create {

        private final CreatePeopleRequest request = PeopleFixture.createPeopleRequest();

        @Test
        @DisplayName("피플 정보를 생성한다.")
        void testCreate() {
            People expected = request.toEntity(mockMember);
            given(peopleRepository.save(any(People.class))).willReturn(expected);

            People actual = peopleService.create(mockMember, request);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("getByPeopleId()는")
    class GetByPeopleId {

        @Test
        @DisplayName("peopleId에 해당하는 피플의 피플 정보를 조회한다.")
        void getByPeople() {
            given(peopleRepository.findById(mockPeople.getPeopleId()))
                .willReturn(Optional.of(mockPeople));

            People actual = peopleService.getByPeopleId(mockPeople.getPeopleId());

            assertThat(actual).isEqualTo(mockPeople);
        }
    }

    @Nested
    @DisplayName("getByMemberId()는")
    class GetByMemberId {

        @Test
        @DisplayName("memberId에 해당하는 회원의 피플 정보를 조회한다.")
        void getByMemberId() {
            given(peopleRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockPeople));

            People actual = peopleService.getByMemberId(mockMember.getMemberId());

            assertThat(actual).isEqualTo(mockPeople);
        }
    }

    @Nested
    @DisplayName("getPeoplePage()는")
    class GetByPeoplePage {

        @Test
        @DisplayName("피플 목록을 페이지 별로 조회한다.")
        void getByPeoplePage() {
            int TEST_SIZE = 10;
            Pageable pageable = PageRequest.of(0, 10);
            List<CardPeopleResponse> expected = PeopleFixture.createCardPeopleResponses(TEST_SIZE);
            Page<CardPeopleResponse> page = new PageImpl<>(expected, pageable, TEST_SIZE);
            given(peopleRepository.findCardPeoplePage(pageable)).willReturn(page);

            List<CardPeopleResponse> actual = peopleService.getCardPeoplePage(pageable)
                .getContent();

            assertThat(actual.size()).isEqualTo(expected.size());

            for (int i = 0; i < TEST_SIZE; i++) {
                final int idx = i;
                assertSoftly(softly -> {
                    softly.assertThat(actual.get(idx).peopleId())
                        .isEqualTo(expected.get(idx).peopleId());
                    softly.assertThat(actual.get(idx).nickname())
                        .isEqualTo(expected.get(idx).nickname());
                    softly.assertThat(actual.get(idx).peopleType())
                        .isEqualTo(expected.get(idx).peopleType());
                    softly.assertThat(actual.get(idx).profileImageUri())
                        .isEqualTo(expected.get(idx).profileImageUri());
                });
            }
        }
    }

    @Nested
    @DisplayName("update()는")
    class Update {

        private final UpdatePeopleRequest request = PeopleFixture.updatePeopleRequest();

        @Test
        @DisplayName("피플 정보를 수정한다.")
        void update() {
            given(peopleRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockPeople));

            People actual = peopleService.update(mockMember.getMemberId(), request);

            assertSoftly(softly -> {
                softly.assertThat(actual.getPeopleId()).isEqualTo(mockPeople.getPeopleId());
                softly.assertThat(actual.getNickname()).isEqualTo(request.nickname());
                softly.assertThat(actual.getEmail()).isEqualTo(request.email());
                softly.assertThat(actual.getPhoneNumber()).isEqualTo(request.phoneNumber());
                softly.assertThat(actual.getPeopleType()).isEqualTo(request.peopleType());
                softly.assertThat(actual.getProfileImageUri()).isEqualTo(request.profileImageUri());
                softly.assertThat(actual.getMember()).isEqualTo(mockMember);
            });
        }
    }

    @Nested
    @DisplayName("delete()는")
    class Delete {

        @Test
        @DisplayName("피플 정보를 삭제한다.")
        void delete() {
            given(peopleRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockPeople));

            peopleService.delete(mockMember.getMemberId());

            verify(peopleRepository).delete(mockPeople);
        }
    }
}