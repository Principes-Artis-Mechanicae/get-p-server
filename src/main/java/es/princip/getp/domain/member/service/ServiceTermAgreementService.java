package es.princip.getp.domain.member.service;

import es.princip.getp.domain.member.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.model.Member;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ServiceTermAgreementService {

    /**
     * 서비스 약관에 동의한다.
     *
     * @param member 회원
     * @param commands 서비스 약관 동의 명령
     * @throws EntityNotFoundException 동의할 서비스 약관이 존재하지 않는 경우
     */
    void agreeServiceTerms(final Member member, List<ServiceTermAgreementCommand> commands);
}
