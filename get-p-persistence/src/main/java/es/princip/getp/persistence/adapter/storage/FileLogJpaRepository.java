package es.princip.getp.persistence.adapter.storage;

import org.springframework.data.jpa.repository.JpaRepository;

interface FileLogJpaRepository extends JpaRepository<FileLogJpaEntity, Long> {
}