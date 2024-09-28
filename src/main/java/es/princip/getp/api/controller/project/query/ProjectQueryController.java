package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.api.controller.project.query.dto.PublicProjectDetailResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.ControllerSupport;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.support.dto.PageResponse;
import es.princip.getp.application.project.commission.command.GetProjectCommand;
import es.princip.getp.application.project.commission.command.ProjectSearchFilter;
import es.princip.getp.application.project.commission.port.in.GetProjectQuery;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
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
    //TODO: 비로그인 사용자의 경우 특정 필드 내용에 대한 필터 처리가 필요함
    @GetMapping("/{projectId}")
    public ResponseEntity<? extends ApiSuccessResult<?>> getProjectByProjectId(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable final Long projectId
    ) {
        final ProjectId pid = new ProjectId(projectId);
        if (isAuthenticated(principalDetails)) {
            final Member member = Optional.ofNullable(principalDetails)
                .map(PrincipalDetails::getMember)
                .orElse(null);
            final MemberId memberId = Optional.ofNullable(principalDetails).map(pd -> pd.getMember().getId()).orElse(null);
            final ProjectDetailResponse response = getProjectQuery.getDetailBy(memberId, pid, member.getMemberType()); 
            return ApiResponse.success(HttpStatus.OK, response);
        }
        final PublicProjectDetailResponse response = getProjectQuery.getPublicDetailBy(pid);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
