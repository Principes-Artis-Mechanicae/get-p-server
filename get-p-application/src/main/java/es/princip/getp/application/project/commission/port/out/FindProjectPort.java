package es.princip.getp.application.project.commission.port.out;

import es.princip.getp.application.project.commission.dto.response.ProjectCardResponse;
import es.princip.getp.application.project.commission.dto.response.ProjectDetailResponse;
import es.princip.getp.application.project.commission.dto.command.ProjectSearchFilter;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindProjectPort {

    Page<ProjectCardResponse> findBy(Pageable pageable, ProjectSearchFilter filter, MemberId memberId);
    
    ProjectDetailResponse findBy(Member member, ProjectId projectId);
}
