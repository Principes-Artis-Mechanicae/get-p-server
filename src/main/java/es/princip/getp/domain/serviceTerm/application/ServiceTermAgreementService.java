package es.princip.getp.domain.serviceTerm.application;

import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.serviceTerm.domain.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermAgreement;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceTermAgreementService {
    private final MemberService memberService;
    private final ServiceTermService serviceTermService;

    @Transactional
    public void agree(Long memberId, List<ServiceTermAgreementRequest> agreementRequests) {
        Member member = memberService.getByMemberId(memberId);
        List<ServiceTermAgreement> agreements = agreementRequests.stream()
            .map(agreementRequest -> {
                String tag = agreementRequest.tag();
                boolean agreed = agreementRequest.agreed();
                ServiceTerm serviceTerm = serviceTermService.getByTag(tag);
                return ServiceTermAgreement.builder()
                        .agreed(agreed)
                        .serviceTerm(serviceTerm)
                        .member(member)
                        .build();
            })
            .toList();
        member.getServiceTermAgreements().addAll(agreements);
    }

    public boolean isAgreedAllRequiredServiceTerms(List<ServiceTermAgreementRequest> agreementRequests) {
        return agreementRequests.stream()
            .allMatch(agreementRequest -> {
                String tag = agreementRequest.tag();
                boolean agreed = agreementRequest.agreed();
                ServiceTerm serviceTerm = serviceTermService.getByTag(tag);
                return !serviceTerm.isRequired() || agreed;
            });
    }
}