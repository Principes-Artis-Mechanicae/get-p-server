package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "people_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id")
    private People people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Builder
    public PeopleLike(final People people, final Client client) {
        this.people = people;
        this.client = client;
    }
}