package es.princip.getp.domain.people.application;

import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.application.command.UpdateMemberCommand;
import es.princip.getp.domain.people.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.domain.people.presentation.dto.response.people.CardPeopleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {

    private final MemberService memberService;
    private final PeopleRepository peopleRepository;

    public Page<CardPeopleResponse> getCardPeoplePage(Pageable pageable) {
        return peopleRepository.findCardPeoplePage(pageable);
    }

    @Transactional
    public Long create(CreatePeopleCommand command) {
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
        People people = peopleRepository.findByMemberId(command.memberId()).orElseThrow();
        people.changeEmail(command.email());
        people.changePeopleType(command.peopleType());
    }

    @Transactional
    public void delete(Long memberId) {
        People people = peopleRepository.findByMemberId(memberId).orElseThrow();
        peopleRepository.delete(people);
    }
}