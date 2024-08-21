package es.princip.getp.api.controller.serviceTerm;

import es.princip.getp.api.controller.serviceTerm.dto.reqeust.ServiceTermRequest;
import es.princip.getp.api.controller.serviceTerm.dto.response.ServiceTermResponse;
import es.princip.getp.application.serviceTerm.port.in.RegisterServiceTermUseCase;
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

    private final RegisterServiceTermUseCase registerServiceTermUseCase;
    
    @PostMapping
    // TODO: 관리자만 접근 가능하도록 권한 제어
    public ServiceTermResponse registerServiceTerm(@RequestBody @Valid ServiceTermRequest serviceTermRequest) {
        return ServiceTermResponse.from(registerServiceTermUseCase.register(serviceTermRequest));
    }
}
