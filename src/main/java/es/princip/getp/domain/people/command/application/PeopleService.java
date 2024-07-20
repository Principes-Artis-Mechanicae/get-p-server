package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.application.command.UpdateMemberCommand;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.infra.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {

    private final MemberService memberService;
    private final PeopleRepository peopleRepository;

    /**
     * 피플 정보를 생성한다.
     *
     * @param command 피플 정보 생성 명령
     * @return 생성된 피플 정보의 ID
     * @throws EntityAlreadyExistsException 이미 등록된 피플 정보가 존재하는 경우
     */
    @Transactional
    public Long create(final CreatePeopleCommand command) {
        if (peopleRepository.existsByMemberId(command.memberId())) {
            throw new EntityAlreadyExistsException("이미 등록된 피플 정보가 존재합니다.");
        }
        memberService.update(UpdateMemberCommand.from(command));
        final People people = People.builder()
            .email(command.email())
            .peopleType(command.peopleType())
            .memberId(command.memberId())
            .build();
        return peopleRepository.save(people).getPeopleId();
    }

    /**
     * 피플 정보를 수정한다.
     *
     * @param command 피플 정보 수정 명령
     * @throws EntityNotFoundException 회원의 피플 정보를 등록하지 않은 경우
     */
    @Transactional
    public void update(final UpdatePeopleCommand command) {
        memberService.update(UpdateMemberCommand.from(command));
        final People people = peopleRepository.findByMemberId(command.memberId()).orElseThrow(
            () -> new EntityNotFoundException("등록된 피플 정보가 없습니다.")
        );
        people.edit(command.email(), command.peopleType());
    }

    /**
     * 피플 정보를 삭제한다.
     *
     * @param memberId 회원 ID
     * @throws EntityNotFoundException 회원의 피플 정보를 등록하지 않은 경우
     */
    @Transactional
    public void delete(final Long memberId) {
        final People people = peopleRepository.findByMemberId(memberId).orElseThrow(
            () -> new EntityNotFoundException("등록된 피플 정보가 없습니다.")
        );
        peopleRepository.delete(people);
    }
}