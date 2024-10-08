package es.princip.getp.persistence.adapter.common.mapper;

import es.princip.getp.domain.common.model.TechStack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechStackPersistenceMapper {

    TechStack mapToTechStack(String value);

    default String mapToString(TechStack techStack) {
        if (techStack == null) {
            return null;
        }
        return techStack.getValue();
    }
}
