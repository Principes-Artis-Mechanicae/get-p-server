package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.like.exception.AlreadyLikedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class LikeService {

    private final LikeRepository likeRepository;

    protected void checkAlreadyLiked(final Long likerId, final Long likedId) {
        if (likeRepository.existsByLikerIdAndLikedId(likerId, likedId)) {
            throw new AlreadyLikedException();
        }
    }

    public abstract Like like(final Likeable liker, final LikeReceivable liked);
}
