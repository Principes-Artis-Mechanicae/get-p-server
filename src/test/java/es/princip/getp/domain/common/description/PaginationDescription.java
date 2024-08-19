package es.princip.getp.domain.common.description;


import static es.princip.getp.infra.util.ParameterDescriptorHelper.getDescriptor;

import org.springframework.restdocs.request.ParameterDescriptor;

public class PaginationDescription {
    
    public static ParameterDescriptor[] description(String sortValue) {
        return new ParameterDescriptor[] {
            getDescriptor("page", "페이지 번호", "default", "0"),
            getDescriptor("size", "페이지 크기", "default", "10"),
            getDescriptor("sort", "정렬 방식", "default", sortValue)
        };
    }
}
