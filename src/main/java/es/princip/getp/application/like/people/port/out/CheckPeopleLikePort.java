package es.princip.getp.application.like.people.port.out;

public interface CheckPeopleLikePort {

    boolean existsBy(Long clientId, Long peopleId);
}
