package es.princip.getp.domain.people.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.exception.NotFoundException;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PeopleRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("People이 DB에 저장 되는지 확인한다")
    void testSave() {
        Member testMember = MemberFixture.createMember();
        People testPeople = PeopleFixture.createPeopleByMember(testMember);
        peopleRepository.save(testPeople);
    }

    @Test
    @DisplayName("멤버 ID로 피플 계정을 조회한다.")
    void testFindByMember_MemberId() {
        Member savedMember = memberRepository.save(MemberFixture.createMember());
        People savedPeople = peopleRepository.save(PeopleFixture.createPeopleByMember(savedMember));

        People result = peopleRepository.findByMember_MemberId(savedMember.getMemberId())
                                            .orElseThrow(() -> new NotFoundException());

        assertThat(result.getPeopleId())
                .isEqualTo(savedPeople.getPeopleId());
    }
}