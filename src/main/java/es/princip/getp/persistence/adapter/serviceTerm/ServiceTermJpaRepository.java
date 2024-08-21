package es.princip.getp.persistence.adapter.serviceTerm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ServiceTermJpaRepository extends JpaRepository<ServiceTermJpaEntity, String> {

    Optional<ServiceTermJpaEntity> findByTag(String tag);

    boolean existsByTag(String tag);
}