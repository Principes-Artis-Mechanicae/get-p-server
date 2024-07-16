package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.application.command.UpdateMemberCommand;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {

    private final MemberService memberService;
    private final PeopleRepository peopleRepository;

    @Transactional
    public Long create(CreatePeopleCommand command) {
        if (peopleRepository.existsByMemberId(command.memberId())) {
            throw new BusinessLogicException(PeopleErrorCode.ALREADY_EXISTS);
        }
        memberService.update(UpdateMemberCommand.from(command));
        People people = People.builder()
            .email(command.email())
            .peopleType(command.peopleType())
            .memberId(command.memberId())
            .build();
        peopleRepository.save(people);
        return people.getPeopleId();
    }

    @Transactional
    public void update(UpdatePeopleCommand command) {
        memberService.update(UpdateMemberCommand.from(command));
        People people = peopleRepository.findByMemberId(command.memberId()).orElseThrow(
            () -> new BusinessLogicException(PeopleErrorCode.NOT_FOUND)
        );
        people.edit(command.memberId(), command.email(), command.peopleType());
    }

    @Transactional
    public void delete(Long memberId) {
        People people = peopleRepository.findByMemberId(memberId).orElseThrow(
            () -> new BusinessLogicException(PeopleErrorCode.NOT_FOUND)
        );
        peopleRepository.delete(people);
    }
}