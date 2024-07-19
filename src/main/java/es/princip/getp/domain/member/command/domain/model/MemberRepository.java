package es.princip.getp.domain.member.command.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(Email email);

    Optional<Member> findByEmail(Email email);
}