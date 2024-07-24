package es.princip.getp.domain.project.query.presentation;

import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.project.query.dto.ProjectDetailResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectQueryController {

    private final ProjectDao projectDao;

    /**
     * 프로젝트 목록 조회
     *
     * @return 프로젝트 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<PageResponse<ProjectCardResponse>>> getProjects(
        @PageableDefault(sort = "PROJECT_ID", direction = Sort.Direction.DESC) final Pageable pageable) {
        final PageResponse<ProjectCardResponse> response = PageResponse.from(projectDao.findPagedProjectCard(pageable));
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
    public ResponseEntity<ApiSuccessResult<ProjectDetailResponse>> getProjectByProjectId(
        @PathVariable final Long projectId) {
        final ProjectDetailResponse response = projectDao.findProjectDetailById(projectId);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
