package es.princip.getp.persistence.adapter.storage;

import es.princip.getp.application.storage.FileLog;
import es.princip.getp.application.storage.port.out.LogFilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class FileLogPersistenceAdapter implements LogFilePort {

    private final FileLogJpaRepository repository;
    private final FileLogPersistenceMapper mapper;

    @Override
    public FileLog log(final FileLog fileLog) {
        return mapper.mapToDomain(repository.save(mapper.mapToJpa(fileLog)));
    }
}
