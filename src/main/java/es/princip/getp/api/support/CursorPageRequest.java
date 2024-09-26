package es.princip.getp.api.support;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class CursorPageRequest implements CursorPageable {

    private final Pageable pageable;
    private final Map<String, String> cursors;

    public CursorPageRequest(final Pageable pageable, final Map<String, String> cursors) {
        this.pageable = pageable;
        this.cursors = cursors;
    }

    public static CursorPageable of(final int size, final Map<String, String> cursor) {
        return new CursorPageRequest(PageRequest.ofSize(size), cursor);
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public Sort.Order getOrder() {
        return pageable.getSort().stream().findFirst().orElse(null);
    }

    @Override
    public String getCursor(final String property) {
        return cursors.get(property);
    }

    @Override
    public boolean hasCursor() {
        return !cursors.isEmpty();
    }
}
