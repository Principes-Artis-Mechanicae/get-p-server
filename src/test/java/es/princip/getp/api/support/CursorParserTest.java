package es.princip.getp.api.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.application.support.Cursor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class CursorParserTest extends ControllerTest {

    @Autowired CursorParser cursorParser;
    @Autowired ObjectMapper objectMapper;

    @Test
    void 커서를_파싱한다() throws JsonProcessingException {
        final Cursor cursor = new Cursor(1L);
        final String cursorJson = objectMapper.writeValueAsString(cursor);
        final String cursorString = Base64.getEncoder().encodeToString(cursorJson.getBytes());

        final Cursor parsed = cursorParser.parse(Cursor.class, cursorString);

        assertThat(parsed).isEqualTo(cursor);
    }

    @Nested
    class 커서_문자열이_올바르지_않으면_null을_반환한다 {

        @Test
        void 커서_문자열이_null이면_null을_반환한다() {
            final Cursor parsed = cursorParser.parse(Cursor.class, null);

            assertThat(parsed).isNull();
        }

        @Test
        void 커서_문자열이_빈_문자열이면_null을_반환한다() {
            final Cursor parsed = cursorParser.parse(Cursor.class, "");

            assertThat(parsed).isNull();
        }

        @Test
        void 커서_문자열이_올바르지_않은_인코딩이면_null을_반환한다() {
            final Cursor parsed = cursorParser.parse(Cursor.class, "invalid");

            assertThat(parsed).isNull();
        }
    }
}