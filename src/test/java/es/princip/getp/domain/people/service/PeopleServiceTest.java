package es.princip.getp.domain.people.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.global.exception.BusinessLogicException;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @InjectMocks
    private PeopleService peopleService;

    @Mock
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("피플 정보를 등록한다")
    void testCreate() {
        //given
        Member testMember = MemberFixture.createMember();
        People testPeople = PeopleFixture.createPeopleByMember(testMember);
        when(peopleRepository.save(any(People.class))).thenReturn(testPeople);

        //when
        People result = peopleRepository.save(testPeople);

        //then
        assertEquals(testPeople, result);
    }

    @Test
    @DisplayName("멤버 ID로 존재하지 않는 피플 계정을 조회한다.")
    void testPeopleNotFoundException() {
        Member testMember = MemberFixture.createMember();
        when(peopleRepository.findByMember_MemberId(testMember.getMemberId())).thenReturn(Optional.empty());

        BusinessLogicException exception = assertThrows(BusinessLogicException.class,
            () ->  peopleService.getByMemberId(testMember.getMemberId()));
        assertEquals(exception.getCode(), PeopleErrorCode.NOTFOUND_DATA.name());
    }
}