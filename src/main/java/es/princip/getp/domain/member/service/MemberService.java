package es.princip.getp.domain.member.service;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.serviceTerm.entity.ServiceTerm;
import es.princip.getp.domain.serviceTerm.service.ServiceTermService;
import es.princip.getp.domain.serviceTermAgreement.entity.ServiceTermAgreement;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.exception.MemberErrorCode;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final ServiceTermService serviceTermService;
    private final MemberRepository memberRepository;
    
    private Member get(Optional<Member> member) {
        return member.orElseThrow(() -> new BusinessLogicException(MemberErrorCode.MEMBER_NOT_FOUND));   
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member getByMemberId(Long memberId) {
        return get(memberRepository.findById(memberId));
    }

    public Member getByEmail(String email) {
        return get(memberRepository.findByEmail(email));
    }

    @Transactional
    public Member create(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
        Member member = signUpRequest.toEntity(passwordEncoder);
        List<ServiceTermAgreement> agreements = signUpRequest.serviceTerms().stream()
            .map(agreement -> {
                String tag = agreement.tag();
                boolean agreed = agreement.agreed();
                ServiceTerm serviceTerm = serviceTermService.getByTag(tag);
                return ServiceTermAgreement.builder()
                    .agreed(agreed)
                    .serviceTerm(serviceTerm)
                    .member(member)
                    .build();
            })
            .toList();
        member.getServiceTermAgreements().addAll(agreements);
        return memberRepository.save(member);
    }
}
