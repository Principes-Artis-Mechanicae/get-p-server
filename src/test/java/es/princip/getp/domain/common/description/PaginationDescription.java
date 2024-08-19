package es.princip.getp.domain.common.description;


import org.springframework.restdocs.request.ParameterDescriptor;

import static es.princip.getp.infra.util.ParameterDescriptorHelper.getDescriptor;

public class PaginationDescription {
    
    public static ParameterDescriptor[] description(final int page, final int size, final String sort) {
        return new ParameterDescriptor[] {
            getDescriptor("page", "페이지 번호", "default", String.valueOf(page)),
            getDescriptor("size", "페이지 크기", "default", String.valueOf(size)),
            getDescriptor("sort", "정렬 방식", "default", sort)
        };
    }
}
