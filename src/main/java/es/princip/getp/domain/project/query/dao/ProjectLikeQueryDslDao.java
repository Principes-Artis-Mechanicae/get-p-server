package es.princip.getp.domain.project.query.dao;

import es.princip.getp.infra.support.QueryDslSupport;
import org.springframework.stereotype.Repository;

import static es.princip.getp.domain.project.command.domain.QProjectLike.projectLike;

@Repository
public class ProjectLikeQueryDslDao extends QueryDslSupport implements ProjectLikeDao {

    // TODO: 좋아요 수 조회 성능 개선 필요
    public Long countByProjectId(final Long projectId) {
        return queryFactory.select(projectLike.count())
            .from(projectLike)
            .where(projectLike.projectId.eq(projectId))
            .fetchOne();
    }
}
