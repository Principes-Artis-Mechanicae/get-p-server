package es.princip.getp.persistence.adapter.common.mapper;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.common.model.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagPersistenceMapper {

    Hashtag mapToHashtag(String value);

    default String mapToString(Hashtag hashtag) {
        if (hashtag == null) {
            return null;
        }
        return hashtag.getValue();
    }

    default HashtagsResponse mapToResponse(List<String> hashtags) {
        return HashtagsResponse.from(hashtags.stream().map(this::mapToHashtag).toList());
    }
}
