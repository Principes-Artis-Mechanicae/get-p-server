package es.princip.getp.domain.people.repository;

import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.global.config.QueryDslTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
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

    /*
    @Test
    @DisplayName("findPeoplePage()는 페이징 처리된 피플 목록을 조회한다.")
    void findPeoplePage() {
        //given
        int TEST_SIZE = 10;
        int PAGE_SIZE = TEST_SIZE / 2;

        List<Member> members = MemberFixture.createMemberList(TEST_SIZE);
        List<People> peoples = PeopleFixture.createPeopleList(members);
        List<People> result = new ArrayList<>(peoples.subList(TEST_SIZE - PAGE_SIZE, TEST_SIZE));

        Collections.reverse(result);

        memberRepository.saveAll(members);
        peopleRepository.saveAll(peoples);

        //when
        PageRequest pageable = PageRequest.of(0, PAGE_SIZE, Sort.by(DESC, "PEOPLE_ID"));

        List<CardPeopleResponse> ant = PeopleFixture.createCardPeopleResponses((long) TEST_SIZE);
        Page<CardPeopleResponse> peoplePage = peopleRepository.findCardPeoplePage(pageable);
    }
    */
}