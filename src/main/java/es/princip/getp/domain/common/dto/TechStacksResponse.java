package es.princip.getp.domain.common.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.princip.getp.domain.common.domain.TechStack;
import lombok.ToString;

import java.io.IOException;
import java.util.List;

@ToString
@JsonSerialize(using = TechStacksResponse.HashtagsResponseSerializer.class)
public class TechStacksResponse {

    private final List<TechStack> techStacks;

    private TechStacksResponse(final List<TechStack> techStacks) {
        this.techStacks = techStacks;
    }

    public static TechStacksResponse from(final List<TechStack> techStacks) {
        return new TechStacksResponse(techStacks);
    }

    static class HashtagsResponseSerializer extends JsonSerializer<TechStacksResponse> {

        @Override
        public void serialize(
            final TechStacksResponse response,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
        ) throws IOException {
            List<String> urls = response.techStacks.stream()
                .map(TechStack::getValue)
                .toList();
            jsonGenerator.writeObject(urls);
        }
    }
}