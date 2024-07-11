package es.princip.getp.domain.serviceTerm.presentation;

import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.domain.serviceTerm.dto.response.ServiceTermResponse;
import es.princip.getp.domain.serviceTerm.service.ServiceTermService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-terms")
@RequiredArgsConstructor
public class ServiceTermController {
    private final ServiceTermService serviceTermService;
    
    @PostMapping
    // TODO: 관리자만 접근 가능하도록 권한 제어
    public ServiceTermResponse createServiceTerm(@RequestBody @Valid ServiceTermRequest serviceTermRequest) {
        return ServiceTermResponse.from(serviceTermService.create(serviceTermRequest));
    }
}
