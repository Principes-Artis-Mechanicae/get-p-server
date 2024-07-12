package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.common.domain.Hashtag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    // 제목
    @Column(name = "title")
    private String title;

    // 금액
    @Column(name = "payment")
    private Long payment;

    // 지원자 모집 마감일
    @Embedded
    private ApplicationDeadline applicationDeadline;

    // 예상 작업 기간
    @Embedded
    private EstimatedDuration estimatedDuration;

    // 상세 설명
    @Column(name = "description")
    private String description;

    // 미팅 유형
    @Column(name = "meeting_type")
    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;

    // 의뢰자
    @Column(name = "client_id")
    private Long clientId;

    // 관심 수
    //TODO: 관심 수를 계산하는 로직 구현 필요
    @Transient
    private int interestsCount;

    // 첨부 파일 목록
    @ElementCollection
    @CollectionTable(name = "project_attachment_file", joinColumns = @JoinColumn(name = "project_id"))
    @Builder.Default
    private List<AttachmentFile> attachmentFiles = new ArrayList<>();

    // 해시태그 목록
    @ElementCollection
    @CollectionTable(name = "project_hashtag", joinColumns = @JoinColumn(name = "project_id"))
    @Builder.Default
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<ProjectApplication> applications = new ArrayList<>();

    public void apply(ProjectApplication application) {
        if (applicationDeadline.isClosed()) {
            throw new IllegalArgumentException("Application is closed");
        }
        applications.add(application);
    }
}
