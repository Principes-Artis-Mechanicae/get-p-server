package es.princip.getp.domain.project.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.global.domain.values.Hashtag;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_hashtag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectHashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Embedded
    private Hashtag hashtag;

    private ProjectHashtag(Project project, Hashtag hashtag) {
        this.project = project;
        this.hashtag = hashtag;
    }

    public static ProjectHashtag of(Project project, String hashtag) {
        return new ProjectHashtag(project, Hashtag.of(hashtag));
    }

    public String getValue() {
        return hashtag.getValue();
    }
}
