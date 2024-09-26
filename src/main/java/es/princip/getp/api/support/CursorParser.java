package es.princip.getp.api.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CursorParser {

    private final ObjectMapper objectMapper;

    /**
     * Base64로 인코딩된 커서 문자열을 맵으로 파싱해요.
     * 만약 커서 문자열이 비어있거나 null이면 빈 맵을 반환해요.
     * 만약 커서 문자열이 올바르지 않다면 IllegalArgumentException을 던져요.
     * 만약 파싱한 커서 맵이 기본키에 해당하는 커서를 포함하지 않는다면 IllegalArgumentException을 던져요.
     *
     * @param cursorString Base64로 인코딩된 커서 문자열
     * @return 커서 맵
     */
    public Map<String, String> parse(final String cursorString) {
        if (cursorString == null || cursorString.isBlank()) {
            return Map.of();
        }
        try {
            final Map<String, String> result = objectMapper.readValue(
                Base64.getDecoder().decode(cursorString),
                new TypeReference<>() {}
            );
            if (result.isEmpty()) {
                throw new IllegalArgumentException("Invalid cursor");
            }
            if (!result.containsKey("id")) {
                throw new IllegalArgumentException("Invalid cursor");
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid cursor");
        }
    }
}
