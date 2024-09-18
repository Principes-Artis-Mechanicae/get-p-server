package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.fixture.like.PeopleLikeFixture;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@RequiredArgsConstructor
public class PeopleLikeDataLoader implements DataLoader {

    private final EntityManager entityManager;
    private final PeopleLikePersistenceMapper mapper;

    @Override
    public void load(final int size) {
        final List<PeopleLikeJpaEntity> likeList = new ArrayList<>();
        LongStream.range(0, size).forEach(clientId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                likeList.add(
                    mapper.mapToJpa(
                        PeopleLikeFixture.peopleLike(1L, new PeopleId(peopleId))
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
