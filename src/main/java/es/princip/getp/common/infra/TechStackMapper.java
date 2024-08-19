package es.princip.getp.common.infra;

import es.princip.getp.common.domain.TechStack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechStackMapper {

    TechStack mapToTechStack(String value);
}
