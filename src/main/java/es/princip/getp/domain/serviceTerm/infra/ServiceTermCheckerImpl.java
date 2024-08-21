package es.princip.getp.domain.serviceTerm.infra;

import es.princip.getp.domain.member.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermChecker;
import es.princip.getp.domain.serviceTerm.exception.NotAgreedAllRequiredServiceTermException;
import es.princip.getp.domain.serviceTerm.port.out.LoadServiceTermPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTermCheckerImpl implements ServiceTermChecker {

    private final LoadServiceTermPort loadServiceTermPort;

    @Override
    public void checkAllRequiredServiceTermsAreAgreed(final List<ServiceTermAgreementCommand> commands) {
        commands.stream()
            .filter(command -> {
                ServiceTerm serviceTerm = loadServiceTermPort.loadBy(command.tag());
                return serviceTerm.isRequired() && !command.agreed();
            })
            .findAny()
            .ifPresent(command -> {
                throw new NotAgreedAllRequiredServiceTermException();
            });
    }
}
