package es.princip.getp.persistence.adapter.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ClientJpaRepository extends JpaRepository<ClientJpaEntity, Long> {

    Optional<ClientJpaEntity> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
