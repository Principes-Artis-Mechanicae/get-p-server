package es.princip.getp.domain.common.infra;

import es.princip.getp.domain.common.domain.TechStack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechStackMapper {

    TechStack mapToTechStack(String value);
}
