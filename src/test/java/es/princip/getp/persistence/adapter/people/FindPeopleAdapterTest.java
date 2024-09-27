package es.princip.getp.persistence.adapter.people;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.application.people.command.PeopleSearchFilter;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.like.people.PeopleLikeDataLoader;
import es.princip.getp.persistence.adapter.member.MemberDataLoader;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FindPeopleAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 20;
    private static final int PAGE_SIZE = 10;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindPeopleAdapter adapter;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new MemberDataLoader(entityManager),
            new PeopleLikeDataLoader(entityManager),
            new PeopleDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 피플_목록을_조회한다() {
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final PeopleSearchFilter filter = new PeopleSearchFilter(null, null);
        final MemberId memberId = new MemberId(1L);
        final Page<CardPeopleResponse> response = adapter.findCardBy(pageable, filter, memberId);

        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
    }

    @Test
    void 좋아요한_피플_목록을_조회한다() {
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final PeopleSearchFilter filter = new PeopleSearchFilter(null, "true");
        final MemberId memberId = new MemberId(1L);
        final Page<CardPeopleResponse> response = adapter.findCardBy(pageable, filter, memberId);

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
    }

    @Test
    void 닉네임으로_피플을_검색한다() {
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final PeopleSearchFilter filter = new PeopleSearchFilter("test1", null);
        final MemberId memberId = new MemberId(1L);
        final Page<CardPeopleResponse> response = adapter.findCardBy(pageable, filter, memberId);

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.getTotalElements()).isEqualTo(11L);
    }

    @Test
    void 피플_ID로_피플_상세_정보를_조회한다() {
        final MemberId memberId = new MemberId(1L);
        final PeopleId peopleId = new PeopleId(1L);
        final DetailPeopleResponse response = adapter.findDetailBy(memberId, peopleId);

        assertThat(response).isNotNull();
        assertThat(response.liked()).isTrue();
    }

    @Test
    void 피플_ID로_피플_공개_상세_정보를_조회한다() {
        final PeopleId peopleId = new PeopleId(1L);
        final PublicDetailPeopleResponse response = adapter.findPublicDetailBy(peopleId);

        assertThat(response).isNotNull();
    }
}