package es.princip.getp.application.project.commission.command;

import es.princip.getp.domain.member.model.Member;
import org.springframework.data.domain.Pageable;

public record GetProjectCommand(
    Pageable pageable,
    ProjectSearchFilter filter,
    Member member
) {
}