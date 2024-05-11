package es.princip.getp.domain.member.service;

import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.dto.request.CreateMemberRequest;
import es.princip.getp.domain.member.exception.MemberErrorCode;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTerm;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTermAgreement;
import es.princip.getp.domain.serviceTerm.service.ServiceTermService;
import es.princip.getp.domain.storage.service.ImageStorageService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final ServiceTermService serviceTermService;
    private final ImageStorageService imageStorageService;
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
    public Member create(CreateMemberRequest request) {
        if (existsByEmail(request.email()))
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        if (!serviceTermService.isAgreedAllRequiredServiceTerms(request.serviceTerms()))
            throw new BusinessLogicException(SignUpErrorCode.NOT_AGREED_REQUIRED_SERVICE_TERM);
        Member member = Member.from(request);
        List<ServiceTermAgreement> agreements = request.serviceTerms().stream()
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

    @Transactional
    public URI updateProfileImage(Member member, MultipartFile image) {
        if (member.getProfileImageUri() != null) {
           imageStorageService.deleteImage(Paths.get(member.getProfileImageUri()));
        }
        Path path = Paths.get(String.valueOf(member.getMemberId())).resolve("profile");
        return URI.create("/" + PathUtil.toURI(imageStorageService.storeImage(path, image)));
    }
}
