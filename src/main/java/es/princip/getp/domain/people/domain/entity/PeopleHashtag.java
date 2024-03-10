package es.princip.getp.domain.people.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.hashtag.domain.values.Hashtag;
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
@Table(name = "people_hashtag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleHashtag extends BaseTimeEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_hashtag_id")
    private Long peopleHashtagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_profile_id")
    private PeopleProfile peopleProfile;

    @Embedded
    private Hashtag hashtag;

    private PeopleHashtag(PeopleProfile peopleProfile, Hashtag hashtag) {
        this.peopleProfile = peopleProfile;
        this.hashtag = hashtag;
    }

    public static PeopleHashtag of(PeopleProfile peopleProfile, String hashtag) {
        return new PeopleHashtag(peopleProfile, Hashtag.from(hashtag));
    }

    public String getValue() {
        return hashtag.getValue();
    }
}
