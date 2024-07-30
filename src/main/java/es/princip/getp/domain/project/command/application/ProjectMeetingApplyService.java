package es.princip.getp.domain.project.command.application;

import static es.princip.getp.domain.project.command.domain.ProjectApplicationStatus.PROCEEDING_MEETING;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.ProjectApplication;
import es.princip.getp.domain.project.command.domain.ProjectApplicationRepository;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;
import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.infra.exception.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectMeetingApplyService {
    
    private final MeetingSender meetingSender;
    
    private final ProjectApplicationRepository projectApplicationRepository;

    private final PeopleRepository peopleRepository;

    private final ProjectDao projectDao;

    @Transactional
    public void applyForProjectMeeting(Long memberId, Long applicationId, ApplyProjectMeetingRequest request) {
        final ProjectApplication projectApplication = projectApplicationRepository.findById(applicationId).orElseThrow();
        
        // 의뢰자 본인 프로젝트인지 검증
        boolean exist = projectDao.existsByProjectIdAndMemberId(projectApplication.getProjectId(), memberId);
        if (!exist) {
            throw new BusinessLogicException("이 프로젝트에 대한 권한이 없습니다.");
        }

        // 프로젝트 지원한 피플이 존재하는지 확인
        final People people = peopleRepository.findById(projectApplication.getApplicantId())
            .orElseThrow(
                () -> new EntityNotFoundException("해당 피플이 존재하지 않습니다.")
            );

        meetingSender.send(people, request);
        projectApplication.setStatus(PROCEEDING_MEETING);
    }
}
