package es.princip.getp.domain.people.query.dao;

import es.princip.getp.domain.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.query.infra.PeopleDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Import(PeopleDaoConfig.class)
public class PeopleDaoTest extends DaoTest {

    public PeopleDaoTest() {
        super(100);
    }

    @Autowired
    private PeopleDao peopleDao;

    private static final int PAGE_SIZE = 10;

    @Test
    @DisplayName("피플 카드 목록 페이지를 조회한다.")
    void findCardPeoplePage() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<CardPeopleResponse> response = peopleDao.findCardPeoplePage(pageable);

        log.info("response: {}", response.getContent());

        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
    }

    @Test
    @DisplayName("피플 ID로 피플 상세 정보를 조회한다.")
    void findDetailPeopleById() {
        DetailPeopleResponse response = peopleDao.findDetailPeopleById(1L);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("피플 ID로 피플 공개 상세 정보를 조회한다.")
    void findPublicDetailPeopleById() {
        PublicDetailPeopleResponse response = peopleDao.findPublicDetailPeopleById(1L);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("멤버 ID로 피플 정보를 조회한다.")
    void findByMemberId() {
        PeopleResponse response = peopleDao.findByMemberId(1L);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("멤버 ID로 피플 상세 프로필을 조회한다.")
    void findDetailPeopleProfileByMemberId() {
        DetailPeopleProfileResponse response = peopleDao.findDetailPeopleProfileByMemberId(1L);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
    }
}