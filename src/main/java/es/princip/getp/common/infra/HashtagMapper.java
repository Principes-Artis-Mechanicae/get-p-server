package es.princip.getp.common.infra;

import es.princip.getp.common.domain.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    Hashtag mapToHashtag(String value);
}
