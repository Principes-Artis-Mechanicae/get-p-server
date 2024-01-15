package es.princip.getp.domain.member.service;

import java.util.Optional;
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
    public Member create(Member member) {
        return memberRepository.save(member);
    }
}
