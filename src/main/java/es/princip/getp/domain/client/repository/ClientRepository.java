package es.princip.getp.domain.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.client.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByMember_MemberId(Long memberId);
}
