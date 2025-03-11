package es.princip.getp.domain.project.apply.model;

public enum ProjectApplicationStatus {
    PENDING_TEAM_APPROVAL, // 팀원 승인 대기
    COMPLETED, // 지원 완료
    MEETING_COMPLETED, // 미팅 완료
    ACCEPTED, // 확정
    CLOSED // 모집 마감
}
