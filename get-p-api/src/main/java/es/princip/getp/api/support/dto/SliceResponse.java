package es.princip.getp.api.support.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
public class SliceResponse<T> {

    private final List<T> content;
    private final SliceInfo sliceInfo;

    private SliceResponse(final List<T> content, final SliceInfo sliceInfo) {
        this.content = content;
        this.sliceInfo = sliceInfo;
    }

    public static <T> SliceResponse<T> of(final Slice<T> slice, final String cursor) {
        SliceInfo pageInfo = SliceInfo.builder()
            .size(slice.getSize())
            .numberOfElements(slice.getNumberOfElements())
            .first(slice.isFirst())
            .last(slice.isLast())
            .empty(slice.isEmpty())
            .sort(slice.getSort())
            .cursor(cursor)
            .build();
        return new SliceResponse<>(slice.getContent(), pageInfo);
    }

    @Getter
    static class SliceInfo {
        private final int size;
        private final int numberOfElements;
        private final boolean first;
        private final boolean last;
        private final boolean empty;
        private final String cursor;
        private final PageResponse.SortInfo sort;

        @Builder
        public SliceInfo(
            final int size,
            final int numberOfElements,
            final boolean first,
            final boolean last,
            final boolean empty,
            final String cursor,
            final Sort sort
        ) {
            this.size = size;
            this.numberOfElements = numberOfElements;
            this.first = first;
            this.last = last;
            this.empty = empty;
            this.cursor = cursor;
            this.sort = sort.stream().map(PageResponse.SortInfo::new).findFirst().orElse(null);
        }
    }
}
