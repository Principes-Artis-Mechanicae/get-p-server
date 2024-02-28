package es.princip.getp.domain.people.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.data.domain.Sort.Direction.DESC;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.PeopleFixture;
import es.princip.getp.global.config.QueryDslTestConfig;
import lombok.extern.slf4j.Slf4j;
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

    @Test
    @DisplayName("People의 페이지네이션이 잘 작동하는지 확인한다")
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

        List<CardPeopleResponse> ant = PeopleFixture.createCardPeopleResponses((long)TEST_SIZE);
        log.info("{} {} {}",ant.get(0).getNickname(),ant.get(1).getNickname(),ant.get(2).getNickname());
        Collections.reverse(ant);

        Page<CardPeopleResponse> peoplePage = peopleRepository.findCardPeoplePage(pageable);
        log.info("{} {} {}",peoplePage.getContent().get(0).getNickname(),peoplePage.getContent().get(1).getNickname(),peoplePage.getContent().get(2).getNickname());

        //then
        assertSoftly(softly -> {
            softly.assertThat(peoplePage.getContent()).isEqualTo(ant.subList(0, PAGE_SIZE));
            softly.assertThat(peoplePage.getTotalElements()).isEqualTo(TEST_SIZE);
        });
    }
}