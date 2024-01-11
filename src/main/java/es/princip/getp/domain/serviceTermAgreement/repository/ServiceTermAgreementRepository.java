package es.princip.getp.domain.serviceTermAgreement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.serviceTermAgreement.entity.ServiceTermAgreement;

public interface ServiceTermAgreementRepository extends JpaRepository<ServiceTermAgreement, Long> {
}