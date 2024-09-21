package es.princip.getp.application.people.service;

import es.princip.getp.application.member.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.application.people.command.RegisterPeopleCommand;
import es.princip.getp.application.people.exception.AlreadyExistsPeopleException;
import es.princip.getp.application.people.mapper.PeopleMapper;
import es.princip.getp.application.people.port.in.RegisterPeopleUseCase;
import es.princip.getp.application.people.port.out.CheckPeoplePort;
import es.princip.getp.application.people.port.out.SavePeoplePort;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterPeopleService implements RegisterPeopleUseCase {

    private final PeopleMapper peopleMapper;

    private final EditMemberUseCase editMemberUseCase;

    private final CheckPeoplePort checkPeoplePort;
    private final SavePeoplePort savePeoplePort;

    /**
     * 피플 정보를 생성한다.
     *
     * @param command 피플 정보 생성 명령
     * @return 생성된 피플 정보의 ID
     * @throws AlreadyExistsPeopleException 이미 등록된 피플 정보가 존재하는 경우
     */
    @Transactional
    public PeopleId register(final RegisterPeopleCommand command) {
        if (checkPeoplePort.existsBy(command.memberId())) {
            throw new AlreadyExistsPeopleException();
        }
        editMemberUseCase.editMember(EditMemberCommand.from(command));
        final People people = peopleMapper.mapToPeople(command.memberId(), command.email());
        return savePeoplePort.save(people);
    }
}