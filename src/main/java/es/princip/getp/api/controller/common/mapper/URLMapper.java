package es.princip.getp.api.controller.common.mapper;

import es.princip.getp.domain.common.model.URL;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface URLMapper {

    URL mapToURL(String url);
}
