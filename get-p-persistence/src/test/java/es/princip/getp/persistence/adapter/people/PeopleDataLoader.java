package es.princip.getp.persistence.adapter.people;

import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.people.ActivityAreaFixture.ACTIVITY_AREA;
import static es.princip.getp.fixture.people.EducationFixture.MAJOR;
import static es.princip.getp.fixture.people.EducationFixture.SCHOOL;
import static es.princip.getp.fixture.people.IntroductionFixture.INTRODUCTION;

@RequiredArgsConstructor
public class PeopleDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<PeopleJpaEntity> peopleList = LongStream.rangeClosed(1, size)
            .mapToObj(this::people)
            .toList()
            .stream()
            .toList();
        peopleList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM PeopleJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE people AUTO_INCREMENT = 1").executeUpdate();
    }

    private PeopleJpaEntity people(final Long memberId) {
        final PeopleProfileJpaVO profile = PeopleProfileJpaVO.builder()
            .introduction(INTRODUCTION)
            .activityArea(ACTIVITY_AREA)
            .school(SCHOOL)
            .major(MAJOR)
            .build();
        return PeopleJpaEntity.builder()
            .memberId(memberId)
            .email("people" + memberId + "@test.com")
            .profile(profile)
            .build();
    }
}
