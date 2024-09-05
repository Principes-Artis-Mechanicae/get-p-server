package es.princip.getp.application.like.project.port.out;

import java.util.Map;

public interface CountProjectLikePort {

    Long countBy(Long projectId);

    Map<Long, Long> countBy(Long... projectIds);
}
