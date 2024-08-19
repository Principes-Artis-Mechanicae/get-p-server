package es.princip.getp.common.infra;

import es.princip.getp.common.domain.URL;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface URLMapper {

    URL mapToURL(String url);
}
