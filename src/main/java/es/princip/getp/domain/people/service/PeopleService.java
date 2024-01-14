package es.princip.getp.domain.people.service;

import org.springframework.stereotype.Service;
import es.princip.getp.domain.people.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

}