package es.princip.getp.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
}