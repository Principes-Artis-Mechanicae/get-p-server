package es.princip.getp.domain.like.query.dao;

import java.util.Map;

public interface LikeDao {

    Long countByLikedId(Long likedId);

    Map<Long, Long> countByLikedIds(Long... likedIds);
}
