package es.princip.getp.application.like.people.service;

import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;

import static org.mockito.BDDMockito.given;

public class LikePeopleMockingUtil {

    static void mockMemberNeverLikedPeople(
        final CheckPeopleLikePort checkPeopleLikePort,
        final MemberId memberId,
        final PeopleId peopleId
    ) {
        given(checkPeopleLikePort.existsBy(memberId, peopleId))
            .willReturn(false);
    }

    static void mockMemberAlreadyLikedPeople(
         final CheckPeopleLikePort checkPeopleLikePort,
         final MemberId memberId,
         final PeopleId peopleId
    ) {
        given(checkPeopleLikePort.existsBy(memberId, peopleId))
            .willReturn(true);
    }
}
