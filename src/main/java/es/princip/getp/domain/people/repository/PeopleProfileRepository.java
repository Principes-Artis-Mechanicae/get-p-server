package es.princip.getp.domain.people.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.princip.getp.domain.people.domain.PeopleProfile;

public interface PeopleProfileRepository extends JpaRepository<PeopleProfile, Long> {
    Optional<PeopleProfile> findByPeople_PeopleId(Long peopleId);

    @Query("select pp from PeopleProfile pp join pp.people p where p.member.memberId = :memberId")
    Optional<PeopleProfile> findByMemberId(@Param("memberId") Long memberId);

    @Query("select pp.activityArea from PeopleProfile pp where pp.people.peopleId = :peopleId")
    String findActivityAreaByPeopleId(@Param("peopleId") Long peopleId);
}
