package es.princip.getp.domain.people.repository;

import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.global.config.QueryDslTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import es.princip.getp.domain.member.entity.Member;

@DataJpaTest
@Import(QueryDslTestConfig.class)
class PeopleQueryDslRepositoryTest {
    @Autowired
    private PeopleQueryDslRepository peopleQueryDslRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("People의 페이지네이션이 잘 작동하는지 확인한다")
    void findPeoplePage() {
        List<Member> testMembers = MemberFixture.createMemberList(10);
        memberRepository.saveAll(testMembers);
        List<People> testPeoples = PeopleFixture.createPeopleListByMember(testMembers, 10);
        peopleRepository.saveAll(testPeoples);
        PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "CREATED_AT"));
        List<People> ant = testPeoples.subList(6, 9);
        Collections.reverse(ant);

        Page<People> peoplePage = peopleQueryDslRepository.findPeoplePage(pageable);

        assertThat(peoplePage.getContent().subList(1, 4)).isEqualTo(ant);
    }
}