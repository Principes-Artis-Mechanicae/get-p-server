package es.princip.getp.domain.project.commission.model;

import es.princip.getp.domain.common.model.Duration;

import java.time.Clock;
import java.time.LocalDate;

public enum ProjectStatus {
    PREPARING, // 모집 준비중
    APPLYING, // 모집중
    PROGRESSING, // 진행중
    COMPLETED, // 완료
    CANCELLED; // 취소

    /**
     * 지원자 모집 기간을 기준으로 프로젝트 상태를 결정합니다. 지원자 모집 기간이 시작됐다면 APPLYING, 아니라면 PREPARING입니다.
     *
     * @param applicationDuration 지원자 모집 기간
     * @return 프로젝트 상태
     */
    public static ProjectStatus determineStatus(final Duration applicationDuration, final Clock clock) {
        final LocalDate now = LocalDate.now(clock);
        if (applicationDuration.isBetween(now)) {
            return APPLYING;
        }
        return PREPARING;
    }
}