package es.princip.getp.domain.serviceTermAgreement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.serviceTerm.entity.ServiceTerm;
import es.princip.getp.domain.serviceTerm.service.ServiceTermService;
import es.princip.getp.domain.serviceTermAgreement.dto.request.ServiceTermAgreementRequest;
import es.princip.getp.domain.serviceTermAgreement.entity.ServiceTermAgreement;
import es.princip.getp.domain.serviceTermAgreement.exception.NotAgreedRequiredServiceTermException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceTermAgreementService {
    private final MemberService memberService;
    private final ServiceTermService serviceTermService;

    @Transactional
    public List<ServiceTermAgreement> agree(Long memberId, List<ServiceTermAgreementRequest> agreementRequests) {
        Member member = memberService.getByMemberId(memberId);
        List<ServiceTermAgreement> agreements = agreementRequests.stream()
            .map(agreementRequest -> {
                String tag = agreementRequest.tag();
                boolean agreed = agreementRequest.agreed();
                ServiceTerm serviceTerm = serviceTermService.getByTag(tag);
                if (serviceTerm.isRequired() && !agreed) {
                    throw new NotAgreedRequiredServiceTermException();
                }
                return ServiceTermAgreement.builder()
                        .agreed(agreed)
                        .serviceTerm(serviceTerm)
                        .member(member)
                        .build();
            })
            .collect(Collectors.toList());
        member.getServiceTermAgreements().addAll(agreements);
        return member.getServiceTermAgreements();
    }
}
