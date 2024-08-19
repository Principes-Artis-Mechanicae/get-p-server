package es.princip.getp.common.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.princip.getp.common.domain.Hashtag;
import lombok.ToString;

import java.io.IOException;
import java.util.List;

@ToString
@JsonSerialize(using = HashtagsResponse.HashtagsResponseSerializer.class)
public class HashtagsResponse {

    private final List<Hashtag> hashtags;

    private HashtagsResponse(final List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public static HashtagsResponse from(final List<Hashtag> hashtags) {
        return new HashtagsResponse(hashtags);
    }

    static class HashtagsResponseSerializer extends JsonSerializer<HashtagsResponse> {

        @Override
        public void serialize(
            final HashtagsResponse response,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
        ) throws IOException {
            List<String> urls = response.hashtags.stream()
                .map(Hashtag::getValue)
                .toList();
            jsonGenerator.writeObject(urls);
        }
    }
}