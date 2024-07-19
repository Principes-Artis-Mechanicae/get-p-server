package es.princip.getp.domain.serviceTerm.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceTermRepository extends JpaRepository<ServiceTerm, ServiceTermTag> {
    Optional<ServiceTerm> findByTag(ServiceTermTag tag);

    boolean existsByTag(ServiceTermTag tag);
}