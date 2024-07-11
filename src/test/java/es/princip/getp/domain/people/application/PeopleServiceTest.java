package es.princip.getp.domain.people.application;

import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.dto.request.UpdateMemberRequest;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.people.fixture.PeopleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @InjectMocks
    private PeopleService peopleService;

    @Mock
    private MemberService memberService;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("create()는")
    class Create {

        private Member mockMember;
        private final CreatePeopleRequest request = createPeopleRequest();

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember(
                request.nickname(),
                request.phoneNumber()
            ));
            given(mockMember.getMemberId()).willReturn(1L);
        }

        @Test
        @DisplayName("피플 정보를 생성한다.")
        void create() {
            People expected = People.from(mockMember, request);
            given(memberService.getByMemberId(mockMember.getMemberId())).willReturn(mockMember);
            given(peopleRepository.save(any(People.class))).willReturn(expected);

            People created = peopleService.create(mockMember.getMemberId(), request);

            verify(memberService).update(mockMember.getMemberId(), UpdateMemberRequest.from(request));
            assertSoftly(softly -> {
                softly.assertThat(created.getNickname()).isEqualTo(request.nickname());
                softly.assertThat(created.getEmail()).isEqualTo(request.email());
                softly.assertThat(created.getPhoneNumber()).isEqualTo(mockMember.getPhoneNumber());
                softly.assertThat(created.getPeopleType()).isEqualTo(request.peopleType());
            });
        }
    }

    @Nested
    @DisplayName("getByPeopleId()는")
    class GetByPeopleId {

        private People mockPeople;

        @BeforeEach
        void setUp() {
            mockPeople = Mockito.spy(createPeople(createMember()));
            given(mockPeople.getPeopleId()).willReturn(1L);
        }

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

        private Member mockMember;
        private People mockPeople;

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember());
            given(mockMember.getMemberId()).willReturn(1L);
            mockPeople = createPeople(mockMember);
        }

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

        private static final int TEST_SIZE = 10;

        @Test
        @DisplayName("피플 목록을 페이지 별로 조회한다.")
        void getByPeoplePage() {
            Pageable pageable = PageRequest.of(0, TEST_SIZE);
            List<CardPeopleResponse> expected = createCardPeopleResponses(TEST_SIZE);
            Page<CardPeopleResponse> page = new PageImpl<>(expected, pageable, TEST_SIZE);
            given(peopleRepository.findCardPeoplePage(pageable)).willReturn(page);

            List<CardPeopleResponse> actual = peopleService.getCardPeoplePage(pageable).getContent();

            assertThat(actual.size()).isEqualTo(expected.size());
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("update()는")
    class Update {

        private Member mockMember;
        private People mockPeople;
        private final UpdatePeopleRequest request = updatePeopleRequest();

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember(
                request.nickname(),
                request.phoneNumber()
            ));
            given(mockMember.getMemberId()).willReturn(1L);
            mockPeople = createPeople(mockMember);
        }

        @Test
        @DisplayName("피플 정보를 수정한다.")
        void update() {
            UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.from(request);
            given(peopleRepository.findByMember_MemberId(mockMember.getMemberId()))
                .willReturn(Optional.of(mockPeople));
            doAnswer(invocation -> {
                mockMember.update(updateMemberRequest);
                return null;
            }).when(memberService).update(anyLong(), any(UpdateMemberRequest.class));

            People updated = peopleService.update(mockMember.getMemberId(), request);

            verify(memberService).update(mockMember.getMemberId(), updateMemberRequest);
            assertSoftly(softly -> {
                softly.assertThat(updated.getNickname()).isEqualTo(request.nickname());
                softly.assertThat(updated.getEmail()).isEqualTo(request.email());
                softly.assertThat(updated.getPhoneNumber()).isEqualTo(request.phoneNumber());
                softly.assertThat(updated.getPeopleType()).isEqualTo(request.peopleType());
            });
        }
    }

    @Nested
    @DisplayName("delete()는")
    class Delete {

        private Member mockMember;
        private People mockPeople;

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember());
            given(mockMember.getMemberId()).willReturn(1L);
            mockPeople = createPeople(mockMember);
        }

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