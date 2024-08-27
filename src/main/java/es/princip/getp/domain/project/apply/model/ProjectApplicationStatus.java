package es.princip.getp.domain.project.apply.model;

public enum ProjectApplicationStatus {
    APPLICATION_COMPLETED, // 지원 완료
    APPLICATION_REJECTED, // 지원 거절됨
    WAITING_MEETING, // 미팅 대기중
    PROCEEDING_MEETING, // 미팅 진행중
    APPLICATION_ACCEPTED, // 승인됨
    APPLICATION_WITHDRAWN // 철회됨
}
