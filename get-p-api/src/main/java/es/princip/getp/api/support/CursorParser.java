package es.princip.getp.api.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.application.support.Cursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CursorParser {

    private final ObjectMapper objectMapper;

    public <T extends Cursor> T parse(final Class<T> clazz, final String cursorString) {
        if (cursorString == null) {
            return null;
        }
        try {
            final T result = objectMapper.readValue(Base64.getDecoder().decode(cursorString), clazz);
            if (result == null) {
                return null;
            }
            if (result.getId() == null) {
                throw new IllegalArgumentException("커서에 기본키가 포함되어 있지 않습니다.");
            }
            return result;
        } catch (final IOException exception) {
            return null;
        }
    }
}
