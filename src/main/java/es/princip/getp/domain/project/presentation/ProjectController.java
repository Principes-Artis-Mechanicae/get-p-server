package es.princip.getp.domain.project.presentation;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberRepository;
import es.princip.getp.domain.project.application.ProjectService;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectRepository;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.dto.response.CardProjectResponse;
import es.princip.getp.domain.project.dto.response.CreateProjectResponse;
import es.princip.getp.domain.project.dto.response.DetailProjectResponse;
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

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ClientRepository clientRepository;
    private final ProjectService projectService;

    /**
     * 프로젝트 의뢰
     *
     * @param request          프로젝트 의뢰 요청
     * @param principalDetails 로그인한 사용자 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreateProjectResponse>> create(
        @RequestBody @Valid CreateProjectRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long memberId = principalDetails.getMember().getMemberId();
        projectService.enroll(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }

    /**
     * 프로젝트 목록 조회
     *
     * @return 프로젝트 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<Page<CardProjectResponse>>> getProjects(
        @PageableDefault(sort = "PROJECT_ID", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CardProjectResponse> response = projectRepository.findToCardProjectResponse(pageable);
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
        Project project = projectRepository.findById(projectId).orElseThrow();
        Client client = clientRepository.findById(project.getClientId()).orElseThrow();
        Member member = memberRepository.findById(client.getMemberId()).orElseThrow();
        DetailProjectResponse response = DetailProjectResponse.from(project, client, member);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}
