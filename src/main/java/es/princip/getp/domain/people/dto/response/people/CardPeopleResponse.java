package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.entity.PeopleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CardPeopleResponse {
    @NotNull 
    private Long peopleId;
    @NotNull 
    private String nickname;
    @NotNull 
    private String peopleType;
    @NotNull 
    private String profileImageUri;
    @Valid
    private CardPeopleProfileResponse profile;
    
    public CardPeopleResponse(final Long peopleId, final String nickname, final PeopleType peopleType,
        final String profileImageUri, final CardPeopleProfileResponse profile) {
        this.peopleId = peopleId;
        this.nickname = nickname;
        this.peopleType = peopleType.name();
        this.profileImageUri = profileImageUri;
        this.profile = profile;
    }
}
