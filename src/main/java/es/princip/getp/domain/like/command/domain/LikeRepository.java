package es.princip.getp.domain.like.command.domain;

public interface LikeRepository {

    boolean existsByLikerIdAndLikedId(Long likerId, Long likedId);

    void deleteByLikerIdAndLikedId(Long likerId, Long likedId);
}