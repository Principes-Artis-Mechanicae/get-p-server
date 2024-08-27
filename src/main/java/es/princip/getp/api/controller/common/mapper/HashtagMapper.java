package es.princip.getp.api.controller.common.mapper;

import es.princip.getp.domain.common.model.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    Hashtag mapToHashtag(String value);
}
