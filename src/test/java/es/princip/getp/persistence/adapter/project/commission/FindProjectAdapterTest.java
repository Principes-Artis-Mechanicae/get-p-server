package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.project.commission.command.ProjectSearchFilter;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.client.ClientDataLoader;
import es.princip.getp.persistence.adapter.member.MemberDataLoader;
import es.princip.getp.persistence.adapter.people.PeopleDataLoader;
import es.princip.getp.persistence.adapter.project.apply.ProjectApplicationDataLoader;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FindProjectAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 20;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindProjectAdapter adapter;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new MemberDataLoader(entityManager),
            new PeopleDataLoader(entityManager),
            new ClientDataLoader(entityManager),
            new ProjectDataLoader(entityManager),
            new ProjectApplicationDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 프로젝트_목록을_조회한다() {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);
        final ProjectSearchFilter filter = new ProjectSearchFilter(
            "null",
            "null",
            "null",
            "null"
        );
        final Page<ProjectCardResponse> response = adapter.findBy(pageable, filter, null);

        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    void 의뢰한_프로젝트_목록을_조회한다() {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);
        final ProjectSearchFilter filter = new ProjectSearchFilter(
            "null",
            "true",
            "null",
            "null"
        );
        final MemberId member = new MemberId(1L);
        final Page<ProjectCardResponse> response = adapter.findBy(pageable, filter, member);

        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    void 지원한_프로젝트_목록을_조회한다() {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);
        final ProjectSearchFilter filter = new ProjectSearchFilter(
            "null",
            "null",
            "true",
            "null"
        );
        final MemberId member = new MemberId(1L);
        final Page<ProjectCardResponse> response = adapter.findBy(pageable, filter, member);

        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    void 프로젝트_상세_정보를_조회한다() {
        final MemberId memberId = new MemberId(1L);
        final ProjectId projectId = new ProjectId(1L);
        final ProjectDetailResponse response = adapter.findBy(memberId, projectId);

        assertThat(response).isNotNull();
    }
}