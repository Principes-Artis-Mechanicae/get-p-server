package es.princip.getp.domain.people.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.global.config.QueryDslTestConfig;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(QueryDslTestConfig.class)
public class PeopleRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @BeforeEach
    void setUp() {
        peopleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("People의 페이지네이션이 잘 작동하는지 확인한다")
    void findPeoplePage() {
        int TEST_SIZE = 10;
        int PAGE_SIZE = TEST_SIZE / 2;

        List<Member> testMembers = MemberFixture.createMemberList(TEST_SIZE);
        memberRepository.saveAll(testMembers);

        List<People> testPeoples = PeopleFixture.createPeopleListByMember(testMembers, TEST_SIZE);
        peopleRepository.saveAll(testPeoples);

        PageRequest pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(DESC, "PEOPLE_ID"));
        List<People> ant = testPeoples.subList(TEST_SIZE - PAGE_SIZE, TEST_SIZE);

        Collections.reverse(ant);

        Page<People> peoplePage = peopleRepository.findPeoplePage(pageable);

        assertThat(peoplePage.getContent()).isEqualTo(ant);
    }
}