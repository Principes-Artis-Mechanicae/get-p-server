package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.like.exception.NeverLikedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Unliker {

    private final LikeRepository likeRepository;

    protected void checkNeverLiked(final Long likerId, final Long likedId) {
        if (!likeRepository.existsByLikerIdAndLikedId(likerId, likedId)) {
            throw new NeverLikedException();
        }
    }

    public void unlike(final Likeable likeable, final LikeReceivable likeReceivable) {
        checkNeverLiked(likeable.getId(), likeReceivable.getId());
        likeRepository.deleteByLikerIdAndLikedId(likeable.getId(), likeReceivable.getId());
    }
}
