package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CursorPageRequest<T extends Cursor> implements CursorPageable<T> {

    private final Pageable pageable;
    private final T cursor;

    public CursorPageRequest(final Pageable pageable, final T cursor) {
        this.pageable = pageable;
        this.cursor = cursor;
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public T getCursor() {
        return cursor;
    }

    @Override
    public boolean hasCursor() {
        return cursor != null;
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }
}
