package es.princip.getp.domain.people.service;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.fixture.PeopleProfileFixture;
import es.princip.getp.domain.people.repository.PeopleProfileRepository;
import es.princip.getp.domain.people.repository.PeopleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static es.princip.getp.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PeopleProfileServiceTest {

    private final Member mockMember = createMember();
    private final People mockPeople = createPeople(mockMember);

    @InjectMocks
    private PeopleProfileService peopleProfileService;

    @Mock
    private PeopleService peopleService;

    @Mock
    private PeopleProfileRepository peopleProfileRepository;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("create()는")
    class Create {

        private final CreatePeopleProfileRequest request
            = PeopleProfileFixture.createPeopleProfileRequest();

        @Test
        @DisplayName("피플 프로필을 생성한다.")
        void create() {
            PeopleProfile expected = request.toEntity(mockPeople);
            given(peopleProfileRepository.save(any(PeopleProfile.class)))
                .willReturn(expected);

            PeopleProfile actual = peopleProfileService.create(
                mockMember.getMemberId(),
                request
            );

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("getByMemberId()는")
    class GetByMemberId {

        @Test
        @DisplayName("memberId에 대한 회원의 피플 프로필을 검색한다.")
        void getByMemberId() {
            PeopleProfile expected = PeopleProfileFixture.createPeopleProfile(mockPeople);
            given(peopleProfileRepository.findByMemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(expected));

            PeopleProfile actual = peopleProfileService.getByMemberId(mockMember.getMemberId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("update()는")
    class Update {

        private final UpdatePeopleProfileRequest request
            = PeopleProfileFixture.updatePeopleProfileRequest();

        @Test
        @DisplayName("피플 프로필을 수정한다.")
        void update() {
            PeopleProfile peopleProfile = PeopleProfileFixture.createPeopleProfile(mockPeople);
            given(peopleProfileRepository.findByMemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(peopleProfile));

            PeopleProfile actual = peopleProfileService.update(
                mockMember.getMemberId(), request);

            assertSoftly(softly -> {
                softly.assertThat(actual.getActivityArea())
                    .isEqualTo(request.activityArea());
                softly.assertThat(actual.getEducation())
                    .isEqualTo(request.education());
                softly.assertThat(actual.getIntroduction())
                    .isEqualTo(request.introduction());
                softly.assertThat(actual.getHashtags())
                    .isEqualTo(request.hashtags());
                softly.assertThat(actual.getTechStacks())
                    .isEqualTo(request.techStacks());
                softly.assertThat(actual.getPortfolios())
                    .isEqualTo(request.portfolios());
            });
        }
    }
}
