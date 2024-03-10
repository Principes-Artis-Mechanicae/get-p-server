package es.princip.getp.domain.serviceTerm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTerm;

public interface ServiceTermRepository extends JpaRepository<ServiceTerm, Long> {
    Optional<ServiceTerm> findByTag(String tag);
}