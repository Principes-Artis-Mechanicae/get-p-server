package es.princip.getp.batch.project.commission;

import es.princip.getp.batch.config.ExtendsWithExecutionTimer;
import es.princip.getp.batch.parallel.ParallelBatchInsertService;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.project.commission.model.ProjectStatus.APPLICATION_OPENED;
import static es.princip.getp.fixture.project.ProjectFixture.project;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParallelBatchInsertProjectService implements BatchInsertProjectService {

    private final BatchInsertProjectJdbcService jdbcService;
    private final ParallelBatchInsertService batchInsertService;

    @ExtendsWithExecutionTimer
    public void insert(final int size) {
        batchInsertService.insert(size, (start, end) -> {
            final List<Project> projects = LongStream.rangeClosed(start, end)
                .boxed()
                .map(id -> project(
                    new ProjectId(id),
                    new ClientId(id),
                    APPLICATION_OPENED
                ))
                .toList();
            jdbcService.batchUpdate(projects);
        });
    }
}
