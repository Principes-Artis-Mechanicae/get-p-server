package es.princip.getp.persistence.adapter.storage;

import es.princip.getp.application.storage.FileLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface FileLogPersistenceMapper {

    @Mapping(source = "memberId", target = "memberId.value")
    FileLog mapToDomain(FileLogJpaEntity fileLogJpaEntity);

    @Mapping(target = "memberId", source = "memberId.value")
    FileLogJpaEntity mapToJpa(FileLog fileLog);
}
