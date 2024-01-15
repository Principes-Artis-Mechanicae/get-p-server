package es.princip.getp.domain.people.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;

@DataJpaTest
public class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("People이 DB에 저장 되는지 확인한다")
    void testSave() {
        Member testMember = MemberFixture.createMember();
        People testPeople = PeopleFixture.createPeopleByMember(testMember);
        peopleRepository.save(testPeople);
    }
}