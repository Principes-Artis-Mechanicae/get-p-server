package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.application.project.apply.dto.response.SearchTeammateResponse;
import es.princip.getp.api.support.CursorPageRequest;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.member.MemberDataLoader;
import es.princip.getp.persistence.adapter.people.PeopleDataLoader;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static es.princip.getp.persistence.adapter.project.apply.ProjectApplicationDataLoader.teamProjectApplication;
import static org.assertj.core.api.Assertions.assertThat;

class FindTeammateAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 10;
    private static final int PAGE_SIZE = 5;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindTeammateAdapter adapter;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new MemberDataLoader(entityManager),
            new PeopleDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 닉네임으로_팀원을_검색한다() {
        final ProjectId projectId = new ProjectId(1L);
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final CursorPageable<Cursor> cursorPageable = new CursorPageRequest<>(pageable, null);

        final Slice<SearchTeammateResponse> response = adapter.findBy(projectId, cursorPageable, "test");

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.hasNext()).isTrue();
    }

    @Test
    void 이미_해당_프로젝트에_지원한_피플은_검색되지_않는다() {
        final ProjectId projectId = new ProjectId(1L);
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final CursorPageable<Cursor> cursorPageable = new CursorPageRequest<>(pageable, null);
        entityManager.persist(teamProjectApplication(1L, 1L, PAGE_SIZE));

        final Slice<SearchTeammateResponse> response = adapter.findBy(projectId, cursorPageable, "test");

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.hasNext()).isFalse();
    }
}