package es.princip.getp.domain.project.repository;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.repository.ClientRepository;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectLike;
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
import static es.princip.getp.domain.project.fixture.ProjectFixture.createProject;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(QueryDslTestConfig.class)
class ProjectLikeRepositoryTest {

    @Autowired
    private ProjectLikeRepository projectLikeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("existsByPeople_PeopleIdAndProject_ProjectId()는")
    class ExistsByPeople_PeopleIdAndProject_ProjectId {

        private final Member peopleMember = createMember("test1@example.com", ROLE_PEOPLE);
        private final People people = createPeople(peopleMember);
        private final Member clientMember = createMember("test2@example.com", ROLE_CLIENT);
        private final Client client = createClient(clientMember);
        private final Project project = createProject(client);

        @BeforeEach
        void setUp() {
            memberRepository.save(peopleMember);
            peopleRepository.save(people);
            memberRepository.save(clientMember);
            clientRepository.save(client);
            projectRepository.save(project);
        }

        @DisplayName("회원이 해당 프로젝트에 이미 좋아요를 눌렀으면 true를 반환한다.")
        @Test
        void existsByMemberAndProject_ProjectId() {
            ProjectLike projectLike = ProjectLike.builder()
                .people(people)
                .project(project)
                .build();
            projectLikeRepository.save(projectLike);

            boolean exists = projectLikeRepository.existsByPeople_PeopleIdAndProject_ProjectId(
                people.getPeopleId(), project.getProjectId()
            );
            assertThat(exists).isTrue();
        }

        @DisplayName("회원이 해당 프로젝트에 좋아요를 누른적이 없으면 false를 반환한다.")
        @Test
        void existsByMemberAndProject_ProjectId_When() {
            boolean exists = projectLikeRepository.existsByPeople_PeopleIdAndProject_ProjectId(
                people.getPeopleId(), project.getProjectId()
            );
            assertThat(exists).isFalse();
        }
    }
}