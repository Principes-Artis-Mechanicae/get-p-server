package es.princip.getp.api.controller.common.mapper;

import es.princip.getp.domain.common.model.TechStack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechStackMapper {

    TechStack mapToTechStack(String value);
}
