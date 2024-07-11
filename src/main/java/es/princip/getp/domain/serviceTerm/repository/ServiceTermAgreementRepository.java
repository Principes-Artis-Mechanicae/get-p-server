package es.princip.getp.domain.serviceTerm.repository;

import es.princip.getp.domain.serviceTerm.domain.ServiceTermAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTermAgreementRepository extends JpaRepository<ServiceTermAgreement, Long> {
}