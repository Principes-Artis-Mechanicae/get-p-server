package es.princip.getp.persistence.adapter.project.apply;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.application.project.apply.dto.response.SearchTeammateResponse;
import es.princip.getp.application.support.Cursor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

class SerializeTeammateCursorAdapterTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SerializeTeammateCursorAdapter adapter = new SerializeTeammateCursorAdapter(objectMapper);

    private final int size = 5;
    private final Pageable pageable = PageRequest.of(0, size);
    private final List<SearchTeammateResponse> content = LongStream.rangeClosed(1, size)
        .mapToObj(i -> new SearchTeammateResponse(i, "test" + i, null))
        .toList();

    @Test
    void 피플_ID로_다음_페이지에_대한_커서를_생성한다() throws JsonProcessingException {
        final Slice<SearchTeammateResponse> response = new SliceImpl<>(content, pageable, true);
        final String expected = objectMapper.writeValueAsString(new Cursor((long) size));

        final String cursor = adapter.serializeCursor(response);

        assertThat(cursor).asBase64Decoded()
            .asString()
            .isEqualTo(expected);
    }

    @Test
    void 다음_페이지가_없으면_null을_반환한다() {
        final Slice<SearchTeammateResponse> response = new SliceImpl<>(content, pageable, false);

        final String cursor = adapter.serializeCursor(response);

        assertThat(cursor).isNull();
    }
}