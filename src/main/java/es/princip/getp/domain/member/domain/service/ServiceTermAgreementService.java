package es.princip.getp.domain.member.domain.service;

import es.princip.getp.domain.member.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.domain.model.Member;

import java.util.List;

public interface ServiceTermAgreementService {

    void agreeServiceTerms(final Member member, List<ServiceTermAgreementCommand> commands);
}
