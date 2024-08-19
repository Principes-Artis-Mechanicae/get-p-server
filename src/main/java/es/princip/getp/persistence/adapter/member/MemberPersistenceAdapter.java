package es.princip.getp.persistence.adapter.member;

import es.princip.getp.application.member.port.out.CheckMemberPort;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.member.port.out.SaveMemberPort;
import es.princip.getp.application.member.port.out.UpdateMemberPort;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SaveMemberPort, LoadMemberPort, CheckMemberPort, UpdateMemberPort {

    private final MemberPersistenceMapper mapper;

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member loadBy(final Email email) {
        final MemberJpaEntity memberJpaEntity = memberJpaRepository.findByEmail(email.getValue())
            .orElseThrow(NotFoundMemberException::new);
        return mapper.mapToDomain(memberJpaEntity);
    }

    @Override
    public Member loadBy(final Long memberId) {
        final MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(memberId)
            .orElseThrow(NotFoundMemberException::new);
        return mapper.mapToDomain(memberJpaEntity);
    }

    @Override
    public Long save(final Member member) {
        final MemberJpaEntity memberJpaEntity = mapper.mapToJpa(member);
        return memberJpaRepository.save(memberJpaEntity).getMemberId();
    }

    @Override
    public void update(final Member member) {
        if (!memberJpaRepository.existsById(member.getMemberId())) {
            throw new NotFoundMemberException();
        }
        save(member);
    }

    @Override
    public boolean existsByEmail(final Email email) {
        return memberJpaRepository.existsByEmail(email.getValue());
    }
}
