package es.princip.getp.domain.serviceTerm.infra;

import es.princip.getp.domain.member.command.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermChecker;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermRepository;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import es.princip.getp.domain.serviceTerm.exception.NotAgreedAllRequiredServiceTermException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTermCheckerImpl implements ServiceTermChecker {

    private final ServiceTermRepository serviceTermRepository;

    private ServiceTerm findByTag(final ServiceTermTag tag) {
        return serviceTermRepository.findByTag(tag).orElseThrow(
            () -> new EntityNotFoundException("해당 서비스 약관이 존재하지 않습니다.")
        );
    }

    @Override
    public void checkAllRequiredServiceTermsAreAgreed(final List<ServiceTermAgreementCommand> commands) {
        commands.stream()
            .filter(command -> {
                ServiceTerm serviceTerm = findByTag(command.tag());
                return serviceTerm.isRequired() && !command.agreed();
            })
            .findAny()
            .ifPresent(command -> {
                throw new NotAgreedAllRequiredServiceTermException();
            });
    }
}
