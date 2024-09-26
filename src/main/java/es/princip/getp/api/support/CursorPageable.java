package es.princip.getp.api.support;

import org.springframework.data.domain.Sort;

public interface CursorPageable {

    int getPageSize();
    Sort.Order getOrder();
    boolean hasCursor();
    String getCursor(String property);
}
