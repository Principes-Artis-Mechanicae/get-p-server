package es.princip.getp.domain.common.infra;

import es.princip.getp.domain.common.domain.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    Hashtag mapToHashtag(String value);
}
