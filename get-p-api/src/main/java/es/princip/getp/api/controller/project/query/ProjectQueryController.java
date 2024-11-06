package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.ControllerSupport;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.commission.dto.command.GetProjectCommand;
import es.princip.getp.application.project.commission.dto.command.ProjectSearchFilter;
import es.princip.getp.application.project.commission.dto.response.ProjectCardResponse;
import es.princip.getp.application.project.commission.dto.response.ProjectDetailResponse;
import es.princip.getp.application.project.commission.port.in.GetProjectQuery;
import es.princip.getp.application.support.dto.PageResponse;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectQueryController extends ControllerSupport{

    private final GetProjectQuery getProjectQuery;

    /**
     * 프로젝트 목록 조회
     *
     * @return 프로젝트 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<PageResponse<ProjectCardResponse>>> getProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @ModelAttribute final ProjectSearchFilter filter,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Member member = Optional.ofNullable(principalDetails)
            .map(PrincipalDetails::getMember)
            .orElse(null);
        final GetProjectCommand command = new GetProjectCommand(pageable, filter, member);
        final PageResponse<ProjectCardResponse> response = PageResponse.from(getProjectQuery.getPagedCards(command));
        return ApiResponse.success(HttpStatus.OK, response);
    }

    /**
     * 프로젝트 상세 조회
     *
     * @param projectId 프로젝트 ID
     * @return 프로젝트
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiSuccessResult<ProjectDetailResponse>> getProjectByProjectId(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable final Long projectId
    ) {
        final ProjectId pid = new ProjectId(projectId);
        final Member member = Optional.ofNullable(principalDetails)
            .map(PrincipalDetails::getMember)
            .orElse(null);
        final ProjectDetailResponse response = getProjectQuery.getDetailBy(member, pid);    
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
