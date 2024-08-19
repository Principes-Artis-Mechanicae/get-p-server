package es.princip.getp.domain.like.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.like.command.domain.people.PeopleLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@RequiredArgsConstructor
public class PeopleLikeDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<PeopleLike> likeList = new ArrayList<>();
        LongStream.range(0, size).forEach(i ->
            LongStream.rangeClosed(1, size).forEach(j ->
                likeList.add(PeopleLike.of(1L, j))
            )
        );
        likeList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM PeopleLike").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE people_like AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
