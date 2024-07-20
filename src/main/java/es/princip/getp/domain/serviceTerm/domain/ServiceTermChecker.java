package es.princip.getp.domain.serviceTerm.domain;

import es.princip.getp.domain.member.command.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.serviceTerm.exception.NotAgreedAllRequiredServiceTermException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ServiceTermChecker {

    /**
     * 모든 필수 약관에 동의했는지 확인한다.
     *
     * @param commands 서비스 약관 동의 명령
     * @throws NotAgreedAllRequiredServiceTermException 필수 약관에 동의하지 않은 경우
     * @throws EntityNotFoundException 동의할 서비스 약관이 존재하지 않는 경우
     */
    void checkAllRequiredServiceTermsAreAgreed(List<ServiceTermAgreementCommand> commands);
}
