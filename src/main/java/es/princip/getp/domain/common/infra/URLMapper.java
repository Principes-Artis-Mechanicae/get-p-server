package es.princip.getp.domain.common.infra;

import es.princip.getp.domain.common.domain.URL;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface URLMapper {

    URL mapToURL(String url);
}
