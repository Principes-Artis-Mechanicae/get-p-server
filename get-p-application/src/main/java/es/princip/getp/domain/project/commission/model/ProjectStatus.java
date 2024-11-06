package es.princip.getp.domain.project.commission.model;

import es.princip.getp.domain.common.model.Duration;

import java.time.Clock;
import java.time.LocalDate;

public enum ProjectStatus {
    PREPARING, // 모집 준비중
    APPLICATION_OPENED, // 모집중
    APPLICATION_CLOSED, // 모집 마감
    MEETING, // 미팅 중
    CONFIRMED, // 확정
    PROGRESSING, // 진행중
    COMPLETED, // 완료
    CANCELLED; // 취소

    /**
     * 지원자 모집 기간을 기준으로 프로젝트 상태를 결정합니다. 지원자 모집 기간이 시작됐다면 APPLICATION_OPENED, 아니라면 PREPARING입니다.
     *
     * @param applicationDuration 지원자 모집 기간
     * @return 프로젝트 상태
     */
    public static ProjectStatus determineStatus(final Duration applicationDuration, final Clock clock) {
        final LocalDate now = LocalDate.now(clock);
        if (applicationDuration.isBetween(now)) {
            return APPLICATION_OPENED;
        }
        return PREPARING;
    }

    public boolean isPreparing() {
        return this == PREPARING;
    }

    public boolean isApplicationOpened() {
        return this == APPLICATION_OPENED;
    }

    public boolean isProgressing() {
        return this == PROGRESSING;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }
}