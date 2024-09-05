package es.princip.getp.persistence.adapter.people;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
import es.princip.getp.persistence.adapter.people.mapper.PeoplePersistenceMapper;
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

public class PeopleQueryAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 20;
    private static final int PAGE_SIZE = 10;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private PeoplePersistenceMapper mapper;
    @Autowired private PeopleQueryAdapter adapter;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new PeopleDataLoader(mapper, entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 피플_카드_목록_페이지를_조회한다() {
        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        final Page<CardPeopleResponse> response = adapter.findCardBy(pageable);

        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
    }

    @Test
    void 피플_ID로_피플_상세_정보를_조회한다() {
        final DetailPeopleResponse response = adapter.findDetailBy(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void 피플_ID로_피플_공개_상세_정보를_조회한다() {
        final PublicDetailPeopleResponse response = adapter.findPublicDetailBy(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void 멤버_ID로_피플_정보를_조회한다() {
        final MyPeopleResponse response = adapter.findBy(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void 멤버_ID로_피플_상세_프로필을_조회한다() {
        final DetailPeopleProfileResponse response = adapter.findDetailProfileBy(1L);

        assertThat(response).isNotNull();
    }
}