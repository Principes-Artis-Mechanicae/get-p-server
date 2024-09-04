package es.princip.getp.application.like.port.out.people;

public interface CheckPeopleLikePort {
    boolean existsByClientIdAndPeopleId(Long clientId, Long peopleId);
}
