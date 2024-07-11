package es.princip.getp.domain.people.infra;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.domain.MemberRepository;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleLike;
import es.princip.getp.domain.people.domain.PeopleLikeRepository;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.infra.config.QueryDslTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static es.princip.getp.domain.client.fixture.ClientFixture.createClient;
import static es.princip.getp.domain.member.domain.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.domain.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(QueryDslTestConfig.class)
class PeopleLikeRepositoryTest {

    @Autowired
    private PeopleLikeRepository peopleLikeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("existsByClient_ClientIdAndPeople_PeopleId()는")
    class ExistsByClient_ClientIdAndPeople_PeopleId {

        private final Member peopleMember = createMember("test1@example.com", ROLE_PEOPLE);
        private final People people = createPeople(peopleMember);
        private final Member clientMember = createMember("test2@example.com", ROLE_CLIENT);
        private final Client client = createClient(clientMember);

        @BeforeEach
        void setUp() {
            memberRepository.save(peopleMember);
            peopleRepository.save(people);
            memberRepository.save(clientMember);
            clientRepository.save(client);
        }

        @DisplayName("의뢰자가 해당 피플에게 이미 좋아요를 눌렀으면 true를 반환한다.")
        @Test
        void existsByClient_ClientIdAndPeople_PeopleId() {
            PeopleLike peopleLike = PeopleLike.builder()
                .client(client)
                .people(people)
                .build();
            peopleLikeRepository.save(peopleLike);

            boolean exists = peopleLikeRepository.existsByClient_ClientIdAndPeople_PeopleId(
                client.getClientId(), people.getPeopleId()
            );
            assertThat(exists).isTrue();
        }

        @DisplayName("의뢰자가 해당 피플에게 좋아요를 누른적이 없으면 false를 반환한다.")
        @Test
        void existsByClient_ClientIdAndPeople_PeopleId_When() {
            boolean exists = peopleLikeRepository.existsByClient_ClientIdAndPeople_PeopleId(
                client.getClientId(), people.getPeopleId()
            );
            assertThat(exists).isFalse();
        }
    }
}