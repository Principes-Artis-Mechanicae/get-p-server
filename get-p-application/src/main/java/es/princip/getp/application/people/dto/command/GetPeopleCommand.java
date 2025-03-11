package es.princip.getp.application.people.dto.command;

import es.princip.getp.domain.member.model.Member;
import org.springframework.data.domain.Pageable;

public record GetPeopleCommand(
    Pageable pageable,
    PeopleSearchFilter filter,
    Member member
) {
}