package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.like.exception.NeverLikedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UnlikeService {

    private final LikeRepository likeRepository;

    protected void checkNeverLiked(final Long likerId, final Long likedId) {
        if (!likeRepository.existsByLikerIdAndLikedId(likerId, likedId)) {
            throw new NeverLikedException();
        }
    }

    public void unlike(final Likeable liker, final LikeReceivable liked) {
        checkNeverLiked(liker.getId(), liked.getId());
        likeRepository.deleteByLikerIdAndLikedId(liker.getId(), liked.getId());
    }
}
