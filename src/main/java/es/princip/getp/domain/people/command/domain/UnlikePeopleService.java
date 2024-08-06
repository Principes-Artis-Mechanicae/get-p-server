package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.like.command.domain.UnlikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnlikePeopleService extends UnlikeService {

    @Autowired
    public UnlikePeopleService(final PeopleLikeRepository peopleLikeRepository) {
        super(peopleLikeRepository);
    }
}
