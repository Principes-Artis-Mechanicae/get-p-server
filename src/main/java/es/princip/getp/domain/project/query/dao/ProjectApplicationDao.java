package es.princip.getp.domain.project.query.dao;

import java.util.Map;

public interface ProjectApplicationDao {

    /**
     * 프로젝트 ID 별 지원자 수를 조회한다.
     *
     * @param projectIds 프로젝트 ID 목록
     * @return (프로젝트 ID, 지원자 수) 맵
     */
    Map<Long, Long> countByProjectIds(Long[] projectIds);
}
