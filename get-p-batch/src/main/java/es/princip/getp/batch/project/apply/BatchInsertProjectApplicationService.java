package es.princip.getp.batch.project.apply;

import es.princip.getp.batch.UniqueLongGenerator;
import es.princip.getp.batch.config.ExtendsWithExecutionTimer;
import es.princip.getp.batch.parallel.ParallelBatchInsertService;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.*;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static es.princip.getp.fixture.project.ProjectApplicationFixture.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchInsertProjectApplicationService {

    private final BatchInsertProjectApplicationJdbcService jdbcService;
    private final ParallelBatchInsertService batchInsertService;

    @ExtendsWithExecutionTimer
    public void insert(final int projectSize) {
        batchInsertService.insert(projectSize, (start, end) -> {
            final List<ProjectApplication> applications = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                final ProjectId projectId = new ProjectId((long) i);
                applications.add(individualProjectApplication(
                    new ProjectApplicationId(UniqueLongGenerator.generateUniqueLong()),
                    new PeopleId(1L),
                    projectId
                ));
                applications.add(teamProjectApplication(
                    new ProjectApplicationId(UniqueLongGenerator.generateUniqueLong()),
                    new PeopleId(2L),
                    projectId,
                    ProjectApplicationStatus.COMPLETED,
                    Set.of(
                        teammate(
                            new TeammateId(UniqueLongGenerator.generateUniqueLong()),
                            new PeopleId(3L),
                            TeammateStatus.APPROVED
                        ),
                        teammate(
                            new TeammateId(UniqueLongGenerator.generateUniqueLong()),
                            new PeopleId(4L),
                            TeammateStatus.APPROVED
                        ),
                        teammate(
                            new TeammateId(UniqueLongGenerator.generateUniqueLong()),
                            new PeopleId(5L),
                            TeammateStatus.APPROVED
                        )
                    )
                ));
            }
            jdbcService.batchUpdate(applications);
        });
    }
}
