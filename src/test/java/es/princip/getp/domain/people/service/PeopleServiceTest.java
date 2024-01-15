package es.princip.getp.domain.people.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;

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
}
