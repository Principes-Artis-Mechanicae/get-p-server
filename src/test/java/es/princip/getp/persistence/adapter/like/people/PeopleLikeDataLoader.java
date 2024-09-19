package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.like.PeopleLikeFixture.peopleLike;

@RequiredArgsConstructor
public class PeopleLikeDataLoader implements DataLoader {

    private final PeopleLikePersistenceMapper mapper;
    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<PeopleLikeJpaEntity> likeList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(memberId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                likeList.add(
                    mapper.mapToJpa(
                        peopleLike(
                            new MemberId(memberId),
                            new PeopleId(peopleId)
                        )
                    )
                )
            )
        );
        likeList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM PeopleLikeJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE people_like AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
