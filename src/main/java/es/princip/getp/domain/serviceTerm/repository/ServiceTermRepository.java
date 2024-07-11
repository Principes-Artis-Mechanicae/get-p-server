package es.princip.getp.domain.serviceTerm.repository;

import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTermRepository extends JpaRepository<ServiceTerm, Long> {
    Optional<ServiceTerm> findByTag(String tag);
}