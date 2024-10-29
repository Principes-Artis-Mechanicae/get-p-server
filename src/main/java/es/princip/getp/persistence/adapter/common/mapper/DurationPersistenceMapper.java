package es.princip.getp.persistence.adapter.common.mapper;

import org.mapstruct.Mapper;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;

@Mapper(componentModel = "spring")
public interface DurationPersistenceMapper {
    
    Duration mapToDomain(DurationJpaVO durationJpaVO);
}
