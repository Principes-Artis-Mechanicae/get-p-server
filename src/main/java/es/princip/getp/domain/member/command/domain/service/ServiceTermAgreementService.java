package es.princip.getp.domain.member.command.domain.service;

import es.princip.getp.domain.member.command.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.command.domain.model.Member;

import java.util.List;

public interface ServiceTermAgreementService {

    void agreeServiceTerms(final Member member, List<ServiceTermAgreementCommand> commands);
}
