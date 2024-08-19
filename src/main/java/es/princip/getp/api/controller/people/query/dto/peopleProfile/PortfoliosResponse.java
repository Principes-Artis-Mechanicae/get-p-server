package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.princip.getp.domain.people.command.domain.Portfolio;

import java.io.IOException;
import java.util.List;

@JsonSerialize(using = PortfoliosResponse.PortfoliosResponseSerializer.class)
public class PortfoliosResponse {

    private final List<Portfolio> portfolios;

    private PortfoliosResponse(final List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public static PortfoliosResponse from(final List<Portfolio> portfolios) {
        return new PortfoliosResponse(portfolios);
    }

    static class PortfoliosResponseSerializer extends JsonSerializer<PortfoliosResponse> {

        @Override
        public void serialize(
            final PortfoliosResponse response,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
        ) throws IOException {
            jsonGenerator.writeStartArray();
            for (Portfolio portfolio : response.portfolios) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("description", portfolio.getDescription());
                jsonGenerator.writeStringField("url", portfolio.getUrl().getValue());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
    }
}