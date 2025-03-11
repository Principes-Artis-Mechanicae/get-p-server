package es.princip.getp.persistence.adapter.common.mapper;

import es.princip.getp.domain.common.model.URL;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface URLPersistenceMapper {

    URL mapToURL(String value);
}
