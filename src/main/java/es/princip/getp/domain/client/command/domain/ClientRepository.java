package es.princip.getp.domain.client.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>{

    Optional<Client> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
