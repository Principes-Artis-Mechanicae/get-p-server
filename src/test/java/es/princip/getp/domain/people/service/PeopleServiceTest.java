package es.princip.getp.domain.people.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
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
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.global.exception.BusinessLogicException;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {
    private final Member testMember = MemberFixture.createMember();
    private final CreatePeopleRequest testCreatePeopleRequest = PeopleFixture.createPeopleRequest();
    private final People testPeople = PeopleFixture.createPeople(testMember);

    @InjectMocks
    private PeopleService peopleService;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("Create()는")
    class create {
        @Test
        @DisplayName("Create를 성공한다.")
        void testCreate() {
            when(peopleRepository.save(any(People.class))).thenReturn(testPeople);

            People createdPeople = peopleService.create(testMember, testCreatePeopleRequest);

            assertSoftly(softly -> {
                softly.assertThat(testPeople.getPeopleId()).isEqualTo(createdPeople.getPeopleId());
                softly.assertThat(testPeople.getNickname()).isEqualTo(createdPeople.getNickname());
                softly.assertThat(testPeople.getEmail()).isEqualTo(createdPeople.getEmail());
                softly.assertThat(testPeople.getPhoneNumber()).isEqualTo(createdPeople.getPhoneNumber());
                softly.assertThat(testPeople.getPeopleType().name()).isEqualTo(createdPeople.getPeopleType().name());
                softly.assertThat(testPeople.getProfileImageUri()).isEqualTo(createdPeople.getProfileImageUri());
                softly.assertThat(testPeople.getAccountNumber()).isEqualTo(createdPeople.getAccountNumber());
                softly.assertThat(testPeople.getMember()).isEqualTo(createdPeople.getMember());
            });
        }
    }

    @Nested
    @DisplayName("Read()는")
    class read {
        @Test
        @DisplayName("PeopleId로 조회를 성공한다.")
        void testGetByPeopleId() {
            when(peopleRepository.findById(testPeople.getPeopleId())).thenReturn(Optional.of(testPeople));

            People result = peopleService.getByPeopleId(testPeople.getPeopleId());

            assertThat(testPeople).isEqualTo(result);
        }

        @Test
        @DisplayName("MemberId로 조회를 성공한다.")
        void testGetByMemberId() {
            when(peopleRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.of(testPeople));

            People result = peopleService.getByMemberId(testMember.getMemberId());

            assertThat(testPeople).isEqualTo(result);
        }

        @Test
        @DisplayName("피플 목록 조회를 성공한다.")
        void testGetPeoplePage() {
            Long TEST_SIZE = 10L;
            Pageable pageable = PageRequest.of(0, 10);
            List<CardPeopleResponse> peoples = PeopleFixture.createCardPeopleResponses(TEST_SIZE);
            Page<CardPeopleResponse> peoplePage = new PageImpl<>(peoples, pageable, TEST_SIZE);
            given(peopleRepository.findCardPeoplePage(pageable)).willReturn(peoplePage);

            Page<CardPeopleResponse> result = peopleService.getCardPeoplePage(pageable);

            assertEquals(result.getContent().size(), TEST_SIZE);
            for(int i = 0; i < TEST_SIZE; i++) {
                final int currentIndex = i;
                assertSoftly(softly -> {
                    softly.assertThat(result.getContent().get(currentIndex).getPeopleId()).isEqualTo(peoples.get(currentIndex).getPeopleId());
                    softly.assertThat(result.getContent().get(currentIndex).getNickname()).isEqualTo(peoples.get(currentIndex).getNickname());
                    softly.assertThat(result.getContent().get(currentIndex).getPeopleType()).isEqualTo(peoples.get(currentIndex).getPeopleType());
                    softly.assertThat(result.getContent().get(currentIndex).getProfileImageUri()).isEqualTo(peoples.get(currentIndex).getProfileImageUri());
                    softly.assertThat(result.getContent().get(currentIndex).getProfile().getActivityArea()).isEqualTo(peoples.get(currentIndex).getProfile().getActivityArea());
                    softly.assertThat(result.getContent().get(currentIndex).getProfile().getHashtags()).isEqualTo(peoples.get(currentIndex).getProfile().getHashtags());
                });
            }            
        }

        @Test
        @DisplayName("피플 정보 또는 피플 프로필이 등록되지 않은 계정이 피플 목록에 존재한다.")
        void testPeoplePageException() {

        }

        @Test
        @DisplayName("멤버 ID로 존재하지 않는 피플 계정을 조회한다.")
        void testPeopleNotFoundException() {
            Member testMember = MemberFixture.createMember();
            when(peopleRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.empty());

            BusinessLogicException exception = assertThrows(BusinessLogicException.class,
                () ->  peopleService.getByMemberId(testMember.getMemberId()));
            assertEquals(exception.getCode(), PeopleErrorCode.PEOPLE_NOT_FOUND.name());
        }
    }

    @Nested
    @DisplayName("Update()는")
    class update {
        private final UpdatePeopleRequest testUpdatePeopleRequest = PeopleFixture.updatePeopleRequest();
        @Test
        @DisplayName("Update를 성공한다.")
        void testUpdate() {
            when(peopleRepository.save(any(People.class))).thenReturn(any(People.class));
            peopleService.create(testMember, testCreatePeopleRequest);
            when(peopleRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.of(testPeople));

            People updatedPeople = peopleService.update(testMember.getMemberId(), testUpdatePeopleRequest);

            assertSoftly(softly -> {
                softly.assertThat(testPeople.getPeopleId()).isEqualTo(updatedPeople.getPeopleId());
                softly.assertThat(testPeople.getNickname()).isEqualTo(updatedPeople.getNickname());
                softly.assertThat(testPeople.getEmail()).isEqualTo(updatedPeople.getEmail());
                softly.assertThat(testPeople.getPhoneNumber()).isEqualTo(updatedPeople.getPhoneNumber());
                softly.assertThat(testPeople.getPeopleType().name()).isEqualTo(updatedPeople.getPeopleType().name());
                softly.assertThat(testPeople.getProfileImageUri()).isEqualTo(updatedPeople.getProfileImageUri());
                softly.assertThat(testPeople.getAccountNumber()).isEqualTo(updatedPeople.getAccountNumber());
                softly.assertThat(testPeople.getMember()).isEqualTo(updatedPeople.getMember());
            });
        }
    }

    @Nested
    @DisplayName("Delete()는")
    class delete {
        @Test
        @DisplayName("Delete를 성공한다.")
        void testDelete() {
            when(peopleRepository.save(any(People.class))).thenReturn(any(People.class));
            peopleService.create(testMember, testCreatePeopleRequest);
            when(peopleRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.of(testPeople));
            doNothing().when(peopleRepository).delete(testPeople);
        
            peopleService.delete(testMember.getMemberId());

            verify(peopleRepository).delete(testPeople);
        }
    }
}