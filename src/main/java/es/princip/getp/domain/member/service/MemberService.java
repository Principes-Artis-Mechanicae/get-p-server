package es.princip.getp.domain.member.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.exception.DuplicatedEmailException;
import es.princip.getp.domain.member.exception.MemberNotFoundException;
import es.princip.getp.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    private Member resolve(Optional<Member> member) {
        return member.orElseThrow(() -> new MemberNotFoundException());   
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member getByMemberId(Long memberId) {
        return resolve(memberRepository.findById(memberId));
    }

    @Transactional
    public Member create(SignUpRequest signUpRequest) {
        if (existsByEmail(signUpRequest.email())) {
            throw new DuplicatedEmailException();
        }
        Member member = memberRepository.save(signUpRequest.toEntity());
        return member;
    }
}
