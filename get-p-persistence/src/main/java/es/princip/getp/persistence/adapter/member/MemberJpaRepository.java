package es.princip.getp.persistence.adapter.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    boolean existsByEmail(String email);

    Optional<MemberJpaEntity> findByEmail(String email);
}