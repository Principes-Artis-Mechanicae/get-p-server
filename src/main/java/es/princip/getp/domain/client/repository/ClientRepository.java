package es.princip.getp.domain.client.repository;

import es.princip.getp.domain.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByMember_MemberId(Long memberId);
}
