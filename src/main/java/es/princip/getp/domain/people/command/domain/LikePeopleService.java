package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.like.command.domain.Like;
import es.princip.getp.domain.like.command.domain.LikeReceivable;
import es.princip.getp.domain.like.command.domain.LikeService;
import es.princip.getp.domain.like.command.domain.Likeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikePeopleService extends LikeService {

    private final PeopleLikeRepository peopleLikeRepository;

    @Autowired
    public LikePeopleService(final PeopleLikeRepository peopleLikeRepository) {
        super(peopleLikeRepository);
        this.peopleLikeRepository = peopleLikeRepository;
    }

    @Override
    public Like like(final Likeable liker, final LikeReceivable liked) {
        checkAlreadyLiked(liker.getId(), liked.getId());
        final PeopleLike like = PeopleLike.of(liker.getId(), liked.getId());
        peopleLikeRepository.save(like);
        return like;
    }
}
