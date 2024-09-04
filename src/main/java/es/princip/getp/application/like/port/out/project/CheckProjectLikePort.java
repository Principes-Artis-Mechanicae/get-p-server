package es.princip.getp.application.like.port.out.project;

public interface CheckProjectLikePort {
    boolean existsByPeopleIdAndProjectId(Long peopleId, Long projectId);
}
