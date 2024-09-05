package es.princip.getp.api.support.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
public class PageResponse<T> {
    private final List<T> content;
    private final PageInfo pageInfo;

    private PageResponse(List<T> content, PageInfo pageInfo) {
        this.content = content;
        this.pageInfo = pageInfo;
    }

    public static <T> PageResponse<T> from(Page<T> page) {
        PageInfo pageInfo = PageInfo.builder()
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .number(page.getNumber())
            .numberOfElements(page.getNumberOfElements())
            .first(page.isFirst())
            .last(page.isLast())
            .empty(page.isEmpty())
            .sort(page.getSort())
            .build();
        return new PageResponse<>(page.getContent(), pageInfo);
    }

    @Getter
    static class SortInfo {
        private final String property;
        private final String direction;

        public SortInfo(Sort.Order order) {
            this.property = order.getProperty();
            this.direction = order.getDirection().name();
        }
    }

    @Getter
    static class PageInfo {
        private final int totalPages;
        private final long totalElements;
        private final int size;
        private final int number;
        private final int numberOfElements;
        private final boolean first;
        private final boolean last;
        private final boolean empty;
        private final SortInfo sort;

        @Builder
        public PageInfo(
            int totalPages,
            long totalElements,
            int size,
            int number,
            int numberOfElements,
            boolean first,
            boolean last,
            boolean empty,
            Sort sort
        ) {
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.size = size;
            this.number = number;
            this.numberOfElements = numberOfElements;
            this.first = first;
            this.last = last;
            this.empty = empty;
            this.sort = sort.stream().map(SortInfo::new).findFirst().orElse(null);
        }
    }
}
