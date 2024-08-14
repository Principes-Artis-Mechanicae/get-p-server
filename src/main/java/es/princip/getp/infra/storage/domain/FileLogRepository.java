package es.princip.getp.infra.storage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileLogRepository extends JpaRepository<FileLog, Long> {
}