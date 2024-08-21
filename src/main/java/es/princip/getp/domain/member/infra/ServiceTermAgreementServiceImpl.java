package es.princip.getp.domain.member.infra;

import es.princip.getp.domain.member.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.ServiceTermAgreement;
import es.princip.getp.domain.member.service.ServiceTermAgreementService;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermChecker;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import es.princip.getp.domain.serviceTerm.port.out.CheckServiceTermPort;
import es.princip.getp.persistence.adapter.serviceTerm.NotFoundServiceTermException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceTermAgreementServiceImpl implements ServiceTermAgreementService {

    private final ServiceTermChecker checker;
    private final CheckServiceTermPort checkServiceTermPort;

    @Override
    public void agreeServiceTerms(final Member member, final List<ServiceTermAgreementCommand> commands) {
        checker.checkAllRequiredServiceTermsAreAgreed(commands);
        Set<ServiceTermAgreement> agreements = commands.stream()
            .map(request -> createServiceTermAgreement(request.tag(), request.agreed()))
            .collect(Collectors.toSet());
        member.agreeServiceTerms(agreements);
    }

    private ServiceTermAgreement createServiceTermAgreement(final ServiceTermTag tag, final boolean agreed) {
        if (!checkServiceTermPort.existsBy(tag)) {
            throw new NotFoundServiceTermException();
        }
        return ServiceTermAgreement.of(tag, agreed);
    }
}
