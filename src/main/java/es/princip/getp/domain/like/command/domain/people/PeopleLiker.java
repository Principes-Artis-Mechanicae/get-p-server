package es.princip.getp.domain.like.command.domain.people;

import es.princip.getp.domain.like.command.domain.LikeReceivable;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.like.command.domain.Liker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeopleLiker extends Liker<PeopleLike> {

    private final PeopleLikeRepository peopleLikeRepository;

    @Autowired
    public PeopleLiker(final PeopleLikeRepository peopleLikeRepository) {
        super(peopleLikeRepository);
        this.peopleLikeRepository = peopleLikeRepository;
    }

    @Override
    public PeopleLike like(final Likeable liker, final LikeReceivable liked) {
        checkAlreadyLiked(liker.getId(), liked.getId());
        final PeopleLike like = PeopleLike.of(liker.getId(), liked.getId());
        peopleLikeRepository.save(like);
        return like;
    }
}
