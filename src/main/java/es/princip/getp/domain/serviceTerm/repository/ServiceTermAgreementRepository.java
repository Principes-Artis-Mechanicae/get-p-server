package es.princip.getp.domain.serviceTerm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTermAgreement;

public interface ServiceTermAgreementRepository extends JpaRepository<ServiceTermAgreement, Long> {
}