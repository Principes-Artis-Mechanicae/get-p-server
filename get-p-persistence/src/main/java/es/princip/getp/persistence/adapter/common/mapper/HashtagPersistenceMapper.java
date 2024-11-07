package es.princip.getp.persistence.adapter.common.mapper;

import es.princip.getp.domain.common.model.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagPersistenceMapper {

    Hashtag mapToHashtag(String value);

    default String mapToString(Hashtag hashtag) {
        if (hashtag == null) {
            return null;
        }
        return hashtag.getValue();
    }
}
