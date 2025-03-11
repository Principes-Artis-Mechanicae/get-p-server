package es.princip.getp.persistence.adapter.member;

import es.princip.getp.application.member.exception.NotFoundMemberException;
import es.princip.getp.application.member.port.out.CheckMemberPort;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.member.port.out.SaveMemberPort;
import es.princip.getp.application.member.port.out.UpdateMemberPort;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SaveMemberPort, LoadMemberPort, CheckMemberPort, UpdateMemberPort {

    private final MemberPersistenceMapper mapper;
    private final MemberJpaRepository repository;

    @Override
    public Member loadBy(final Email email) {
        final MemberJpaEntity memberJpaEntity = repository.findByEmail(email.getValue())
            .orElseThrow(NotFoundMemberException::new);
        return mapper.mapToDomain(memberJpaEntity);
    }

    @Override
    public Member loadBy(final MemberId memberId) {
        final MemberJpaEntity memberJpaEntity = repository.findById(memberId.getValue())
            .orElseThrow(NotFoundMemberException::new);
        return mapper.mapToDomain(memberJpaEntity);
    }

    @Override
    public MemberId save(final Member member) {
        final MemberJpaEntity memberJpaEntity = mapper.mapToJpa(member);
        final Long id = repository.save(memberJpaEntity).getId();
        return new MemberId(id);
    }

    @Override
    public void update(final Member member) {
        if (!repository.existsById(member.getId().getValue())) {
            throw new NotFoundMemberException();
        }
        save(member);
    }

    @Override
    public boolean existsBy(final Email email) {
        return repository.existsByEmail(email.getValue());
    }
}
