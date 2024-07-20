package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.application.command.UpdateMemberCommand;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.command.domain.PeopleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.NicknameFixture.nickname;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.phoneNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@RunWith(Enclosed.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
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

        private final Long peopleId = 1L;
        private final CreatePeopleCommand command = new CreatePeopleCommand(
            1L, nickname(), email(), phoneNumber(), PeopleType.INDIVIDUAL
        );

        @DisplayName("피플 정보를 생성한다.")
        @Test
        void create() {
            given(peopleRepository.existsByMemberId(command.memberId())).willReturn(false);
            final People people = spy(People.class);
            given(people.getPeopleId()).willReturn(peopleId);
            given(peopleRepository.save(any(People.class))).willReturn(people);

            final Long peopleId = peopleService.create(command);

            assertThat(peopleId).isEqualTo(this.peopleId);
            verify(memberService, times(1)).update(any(UpdateMemberCommand.class));
            verify(peopleRepository, times(1)).save(any(People.class));
        }
    }

    @Nested
    @DisplayName("update()는")
    class Update {

        private final UpdatePeopleCommand command = new UpdatePeopleCommand(
            1L, nickname(), email(), phoneNumber(), PeopleType.INDIVIDUAL
        );

        @Test
        @DisplayName("피플 정보를 수정한다.")
        void update() {
            final People people = spy(People.class);
            willDoNothing().given(people)
                .edit(command.email(), command.peopleType());
            given(peopleRepository.findByMemberId(command.memberId()))
                .willReturn(Optional.of(people));

            peopleService.update(command);

            verify(memberService, times(1)).update(any(UpdateMemberCommand.class));
            verify(people, times(1)).edit(command.email(), command.peopleType());
        }
    }

    @Nested
    @DisplayName("delete()는")
    class Delete {

        @Test
        @DisplayName("피플 정보를 삭제한다.")
        void delete() {
            final Long memberId = 1L;
            final People people = mock(People.class);
            given(peopleRepository.findByMemberId(memberId)).willReturn(Optional.of(people));

            peopleService.delete(memberId);

            verify(peopleRepository, times(1)).delete(people);
        }
    }
}