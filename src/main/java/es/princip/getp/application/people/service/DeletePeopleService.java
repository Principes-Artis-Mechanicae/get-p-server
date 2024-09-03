package es.princip.getp.application.people.service;

import es.princip.getp.application.people.port.out.DeletePeoplePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.people.model.People;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeletePeopleService {

    private final LoadPeoplePort loadPeoplePort;
    private final DeletePeoplePort deletePeoplePort;

    @Transactional
    public void delete(final Long memberId) {
        final People people = loadPeoplePort.loadBy(memberId);
        deletePeoplePort.delete(people.getId());
    }
}