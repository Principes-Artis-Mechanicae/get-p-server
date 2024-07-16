package es.princip.getp.domain.member.infra;

import es.princip.getp.domain.member.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.ServiceTermAgreement;
import es.princip.getp.domain.member.domain.service.ServiceTermAgreementService;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermChecker;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermRepository;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceTermAgreementServiceImpl implements ServiceTermAgreementService {

    private final ServiceTermChecker checker;
    private final ServiceTermRepository serviceTermRepository;

    @Override
    public void agreeServiceTerms(final Member member, final List<ServiceTermAgreementCommand> commands) {
        if (checker.isAgreedAllRequiredServiceTerms(commands)) {
            throw new IllegalArgumentException("Not agreed all required service terms");
        }
        Set<ServiceTermAgreement> agreements = commands.stream()
            .map(request -> createServiceTermAgreement(request.tag(), request.agreed()))
            .collect(Collectors.toSet());
        member.agreeServiceTerms(agreements);
    }

    private ServiceTermAgreement createServiceTermAgreement(final ServiceTermTag tag, final boolean agreed) {
        if (serviceTermRepository.existsByTag(tag)) {
            throw new IllegalArgumentException("ServiceTerm not found");
        }
        return new ServiceTermAgreement(tag, agreed);
    }
}