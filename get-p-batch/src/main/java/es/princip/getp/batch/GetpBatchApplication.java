package es.princip.getp.batch;

import es.princip.getp.batch.project.DropProjectService;
import es.princip.getp.batch.project.ParallelBatchInsertProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GetpBatchApplication implements CommandLineRunner {

    @Autowired private DropProjectService dropProjectService;
    @Autowired private ParallelBatchInsertProjectService batchInsertProjectService;

    public static void main(String[] args) {
        SpringApplication.run(GetpBatchApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        dropProjectService.dropProject();
        batchInsertProjectService.insert(100_000);
    }
}
