package es.princip.getp.domain.member.command.domain.command;

import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;

public record ServiceTermAgreementCommand(
    ServiceTermTag tag,
    boolean agreed
) {
}