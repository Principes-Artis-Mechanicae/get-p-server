package es.princip.getp.batch;

import es.princip.getp.batch.project.commission.BatchDeleteProjectService;
import es.princip.getp.batch.project.commission.ParallelBatchInsertProjectService;
import es.princip.getp.batch.project.apply.BatchDeleteProjectApplicationService;
import es.princip.getp.batch.project.apply.BatchInsertProjectApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GetpBatchApplication implements CommandLineRunner {

    @Autowired private BatchDeleteProjectApplicationService batchDeleteProjectApplicationService;
    @Autowired private BatchDeleteProjectService batchDeleteProjectService;

    @Autowired private BatchInsertProjectApplicationService batchInsertProjectApplicationService;
    @Autowired private ParallelBatchInsertProjectService batchInsertProjectService;

    public static void main(String[] args) {
        SpringApplication.run(GetpBatchApplication.class, args);
    }

    private static final int PROJECT_SIZE = 100_000;
    @Override
    public void run(final String... args) {
        batchDeleteProjectApplicationService.delete();
        batchDeleteProjectService.delete();

        batchInsertProjectService.insert(PROJECT_SIZE);
        batchInsertProjectApplicationService.insert(PROJECT_SIZE);
    }
}
