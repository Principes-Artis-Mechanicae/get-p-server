package es.princip.getp.domain.like.query.infra;

import es.princip.getp.domain.like.command.domain.people.PeopleLike;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@RequiredArgsConstructor
public class PeopleLikeDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        loadPeopleLike(size);
    }

    private void loadPeopleLike(final int size) {
        final List<PeopleLike> likeList = new ArrayList<>();
        LongStream.range(0, size).forEach(i ->
            LongStream.rangeClosed(1, size).forEach(j ->
                likeList.add(PeopleLike.of(1L, j))
            )
        );
        likeList.forEach(entityManager::persist);
    }
}
