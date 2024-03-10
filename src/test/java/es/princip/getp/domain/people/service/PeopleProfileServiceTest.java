package es.princip.getp.domain.people.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.entity.PeopleTechStack;
import es.princip.getp.domain.people.repository.PeopleProfileRepository;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.fixture.PeopleProfileFixture;
import es.princip.getp.global.validator.CommonValidator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PeopleProfileServiceTest {
    private final Member testMember = MemberFixture.createMember();
    private final People testPeople = PeopleFixture.createPeople(testMember);
    private final CreatePeopleProfileRequest testCreatePeopleProfileRequest = PeopleProfileFixture.createPeopleProfileRequest();
    private final PeopleProfile testPeopleProfile = testCreatePeopleProfileRequest.toEntity(testPeople);

    @InjectMocks
    private PeopleProfileService peopleProfileService;

    @Mock
    private PeopleService peopleService;

    @Mock
    private PeopleProfileRepository peopleProfileRepository;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("Create()는")
    class create {
        @Test
        @DisplayName("Create를 성공한다.")
        void testCreate() {
            given(peopleProfileRepository.save(any(PeopleProfile.class))).willReturn(testPeopleProfile);

            PeopleProfile createdPeopleProfile = peopleProfileService.create(testMember.getMemberId(), testCreatePeopleProfileRequest);

            assertSoftly(softly -> {
                softly.assertThat(testPeopleProfile.getPeopleProfileId()).isEqualTo(createdPeopleProfile.getPeopleProfileId());
                softly.assertThat(testPeopleProfile.getActivityArea()).isEqualTo(createdPeopleProfile.getActivityArea());
                softly.assertThat(testPeopleProfile.getEducation()).isEqualTo(createdPeopleProfile.getEducation());
                softly.assertThat(testPeopleProfile.getIntroduction()).isEqualTo(createdPeopleProfile.getIntroduction());
                softly.assertThat(testPeopleProfile.getHashtags()).isEqualTo(createdPeopleProfile.getHashtags());
                softly.assertThat(testPeopleProfile.getTechStacks()).isEqualTo(createdPeopleProfile.getTechStacks());
            });
        }
    }

    @Nested
    @DisplayName("Read()는")
    class read {
    }

    @Nested
    @DisplayName("Update()는")
    class update {
        private final UpdatePeopleProfileRequest testUpdatePeopleProfileRequest = PeopleProfileFixture.updatePeopleProfileRequest();

        @Test
        @DisplayName("update를 성공한다.")
        void testUpdate() {
            given(peopleProfileRepository.findByMemberId(testMember.getMemberId())).willReturn(Optional.of(testPeopleProfile));

            PeopleProfile updatedPeopleProfile = peopleProfileService.update(testMember.getMemberId(), testUpdatePeopleProfileRequest);

            assertSoftly(softly -> {
                softly.assertThat(testUpdatePeopleProfileRequest.activityArea()).isEqualTo(updatedPeopleProfile.getActivityArea());
                softly.assertThat(testUpdatePeopleProfileRequest.education()).isEqualTo(updatedPeopleProfile.getEducation());
                softly.assertThat(testUpdatePeopleProfileRequest.introduction()).isEqualTo(updatedPeopleProfile.getIntroduction());
                
                //Hashtag 검증
                List<String> testHashtags = testUpdatePeopleProfileRequest.hashtags();
                List<String> updatedHashtags = updatedPeopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList();
                CommonValidator.assertStringListEquals(testHashtags, updatedHashtags);

                //TechStack 검증
                List<String> testTechStacks = testUpdatePeopleProfileRequest.techStacks();
                List<String> updatedTechStacks = updatedPeopleProfile.getTechStacks().stream().map(PeopleTechStack::getValue).toList();
                CommonValidator.assertStringListEquals(testTechStacks, updatedTechStacks);
            });
        }
    }
}
