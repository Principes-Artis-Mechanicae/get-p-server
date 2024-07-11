package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.hashtag.domain.Hashtag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people_hashtag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleHashtag extends BaseTimeEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_profile_id")
    private PeopleProfile peopleProfile;

    @Embedded
    @Getter
    private Hashtag hashtag;

    private PeopleHashtag(PeopleProfile peopleProfile, Hashtag hashtag) {
        this.peopleProfile = peopleProfile;
        this.hashtag = hashtag;
    }

    public static PeopleHashtag of(PeopleProfile peopleProfile, Hashtag hashtag) {
        return new PeopleHashtag(peopleProfile, hashtag);
    }
}
