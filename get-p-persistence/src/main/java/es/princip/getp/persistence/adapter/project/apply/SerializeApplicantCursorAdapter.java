package es.princip.getp.persistence.adapter.project.apply;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.application.project.apply.dto.response.ProjectApplicantResponse;
import es.princip.getp.application.project.apply.port.out.SerializeApplicantCursorPort;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.persistence.support.SerializeCursorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
class SerializeApplicantCursorAdapter implements SerializeApplicantCursorPort {

    private final ObjectMapper objectMapper;

    @Override
    public String serializeCursor(final Slice<ProjectApplicantResponse> slice) {
        if (!slice.hasNext()) {
            return null;
        }
        final Long id = slice.getContent()
            .stream()
            .reduce((first, second) -> second)
            .map(ProjectApplicantResponse::peopleId)
            .orElse(null);
        if (id == null) {
            return null;
        }
        final Cursor cursor = new Cursor(id);
        try {
            final String cursorJson = objectMapper.writeValueAsString(cursor);
            return Base64.getEncoder().encodeToString(cursorJson.getBytes());
        } catch (final JsonProcessingException exception) {
            log.debug("Failed to serialize cursor", exception);
            throw new SerializeCursorException();
        }
    }
}