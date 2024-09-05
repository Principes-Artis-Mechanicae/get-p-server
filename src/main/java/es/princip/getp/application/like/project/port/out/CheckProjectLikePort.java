package es.princip.getp.application.like.project.port.out;

public interface CheckProjectLikePort {

    boolean existsBy(Long peopleId, Long projectId);
}
