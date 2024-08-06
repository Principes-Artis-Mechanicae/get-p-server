package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.like.exception.AlreadyLikedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Liker<T extends Like> {

    private final LikeRepository likeRepository;

    protected void checkAlreadyLiked(final Long likerId, final Long likedId) {
        if (likeRepository.existsByLikerIdAndLikedId(likerId, likedId)) {
            throw new AlreadyLikedException();
        }
    }

    public abstract T like(final Likeable likeable, final LikeReceivable likeReceivable);
}
