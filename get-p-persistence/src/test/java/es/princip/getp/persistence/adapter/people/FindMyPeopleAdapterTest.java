package es.princip.getp.persistence.adapter.people;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.persistence.adapter.member.MemberDataLoader;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindMyPeopleAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 1;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindMyPeopleAdapter adapter;

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
    void 멤버_ID로_피플_정보를_조회한다() {
        final MemberId memberId = new MemberId(1L);
        final MyPeopleResponse response = adapter.findBy(memberId);

        assertThat(response).isNotNull();
    }

    @Test
    void 멤버_ID로_피플_상세_프로필을_조회한다() {
        final MemberId memberId = new MemberId(1L);
        final PeopleProfileDetailResponse response = adapter.findDetailProfileBy(memberId);

        assertThat(response).isNotNull();
    }
}