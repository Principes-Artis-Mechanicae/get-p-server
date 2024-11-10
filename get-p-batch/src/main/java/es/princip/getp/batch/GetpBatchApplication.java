package es.princip.getp.batch;

import es.princip.getp.batch.project.BatchInsertProjectService;
import es.princip.getp.batch.project.DropProjectService;
import es.princip.getp.batch.project.ProjectFactory;
import es.princip.getp.domain.project.commission.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class GetpBatchApplication implements CommandLineRunner {

    @Autowired private DropProjectService dropProjectService;
    @Autowired private ProjectFactory projectFactory;
    @Autowired private BatchInsertProjectService batchInsertProjectService;

    public static void main(String[] args) {
        SpringApplication.run(GetpBatchApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        dropProjectService.dropProject();
        final List<Project> projects = projectFactory.createProjects(100_000);
        batchInsertProjectService.batchInsert(projects);
    }
}
