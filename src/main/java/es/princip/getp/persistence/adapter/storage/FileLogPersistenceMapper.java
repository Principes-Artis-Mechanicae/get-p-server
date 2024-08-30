package es.princip.getp.persistence.adapter.storage;

import es.princip.getp.application.storage.FileLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface FileLogPersistenceMapper {

    FileLog mapToDomain(FileLogJpaEntity fileLogJpaEntity);

    FileLogJpaEntity mapToJpa(FileLog fileLog);
}
