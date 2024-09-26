package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.api.support.CursorPageRequest;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
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

import static org.assertj.core.api.Assertions.assertThat;

class FindTeammateAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 20;
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
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final CursorPageable<Cursor> cursorPageable = new CursorPageRequest<>(pageable, null);

        final Slice<SearchTeammateResponse> response = adapter.findBy(cursorPageable, "test");

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.hasNext()).isTrue();
    }
}