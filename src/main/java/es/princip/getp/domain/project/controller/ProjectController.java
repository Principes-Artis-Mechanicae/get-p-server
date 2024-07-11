package es.princip.getp.domain.project.controller;

import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.dto.response.CardProjectResponse;
import es.princip.getp.domain.project.dto.response.CreateProjectResponse;
import es.princip.getp.domain.project.dto.response.DetailProjectResponse;
import es.princip.getp.domain.project.service.ProjectService;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 의뢰
     *
     * @param request          프로젝트 의뢰 요청
     * @param principalDetails 로그인한 사용자 정보
     * @return 의뢰된 프로젝트
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreateProjectResponse>> create(
        @RequestBody @Valid CreateProjectRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long memberId = principalDetails.getMember().getMemberId();
        CreateProjectResponse response = CreateProjectResponse.from(
            projectService.create(memberId, request));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, response));
    }

    /**
     * 프로젝트 목록 조회
     *
     * @return 프로젝트 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<Page<CardProjectResponse>>> getProjects(
        @PageableDefault(sort = "PROJECT_ID", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CardProjectResponse> response = projectService.getProjectPage(pageable)
            .map(CardProjectResponse::from);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 프로젝트 상세 조회
     *
     * @param projectId 프로젝트 ID
     * @return 프로젝트
     */
    //TODO: 비로그인 사용자의 경우 특정 필드 내용에 대한 필터 처리가 필요함
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiSuccessResult<DetailProjectResponse>> getProject(
        @PathVariable Long projectId) {
        DetailProjectResponse response = DetailProjectResponse.from(
            projectService.getByProjectId(projectId));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}
