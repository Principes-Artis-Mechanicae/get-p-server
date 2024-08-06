package es.princip.getp.domain.like.command.domain.people;

import es.princip.getp.domain.like.command.domain.Unliker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleUnliker extends Unliker {

    @Autowired
    public PeopleUnliker(final PeopleLikeRepository peopleLikeRepository) {
        super(peopleLikeRepository);
    }
}
