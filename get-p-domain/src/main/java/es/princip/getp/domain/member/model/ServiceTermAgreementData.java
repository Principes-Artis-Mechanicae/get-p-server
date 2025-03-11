package es.princip.getp.domain.member.model;

import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;

public record ServiceTermAgreementData(
    ServiceTermTag tag,
    boolean agreed
) {
}