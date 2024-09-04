package es.princip.getp.application.like.port.out.project;

import java.util.Map;

public interface CountProjectLikePort {
    Long countByprojectId(Long projectId);

    Map<Long, Long> countByprojectIds(Long... projectIds);
}
