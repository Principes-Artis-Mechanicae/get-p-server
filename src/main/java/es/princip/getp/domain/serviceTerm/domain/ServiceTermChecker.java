package es.princip.getp.domain.serviceTerm.domain;

import es.princip.getp.domain.member.command.domain.command.ServiceTermAgreementCommand;

import java.util.List;

public interface ServiceTermChecker {

    boolean isAgreedAllRequiredServiceTerms(List<ServiceTermAgreementCommand> commands);
}
