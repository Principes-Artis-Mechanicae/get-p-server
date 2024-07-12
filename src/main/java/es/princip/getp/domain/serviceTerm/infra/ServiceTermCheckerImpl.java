package es.princip.getp.domain.serviceTerm.infra;

import es.princip.getp.domain.member.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermChecker;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTermCheckerImpl implements ServiceTermChecker {

    private final ServiceTermRepository serviceTermRepository;

    public boolean isAgreedAllRequiredServiceTerms(List<ServiceTermAgreementCommand> commands) {
        return commands.stream().allMatch(command -> {
            ServiceTerm serviceTerm = serviceTermRepository.findByTag(command.tag()).orElseThrow();
            return !serviceTerm.isRequired() || command.agreed();
        });
    }
}
