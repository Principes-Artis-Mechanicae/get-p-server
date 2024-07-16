package es.princip.getp.domain.people.query.dao;

import es.princip.getp.infra.annotation.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DataTest
public class PeopleDaoTest {

    @Autowired
    private PeopleDao peopleDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("피플 카드 목록 페이지를 조회한다.")
    void findCardPeoplePage() {

    }

    @Test
    @DisplayName("피플 ID로 피플 상세 정보를 조회한다.")
    void findDetailPeopleById() {
    }

    @Test
    @DisplayName("피플 ID로 피플 공개 상세 정보를 조회한다.")
    void findPublicDetailPeopleById() {
    }

    @Test
    @DisplayName("멤버 ID로 피플 정보를 조회한다.")
    void findByMemberId() {
    }

    @Test
    @DisplayName("멤버 ID로 피플 상세 프로필을 조회한다.")
    void findDetailPeopleProfileByMemberId() {
    }
}