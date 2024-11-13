package es.princip.getp.batch.project.apply;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
class BatchInsertProjectApplicationAttachmentFileJdbcService {

    private record ProjectApplicationIdAttachmentFile(
        ProjectApplicationId applicationId,
        AttachmentFile attachmentFile
    ) {
    }

    private final JdbcTemplate jdbcTemplate;
    private static final String sql =
        """
        insert into project_application_attachment_file(
            project_application_id,
            attachment_files
        ) values (?, ?);
        """;

    public void batchUpdate(final List<ProjectApplication> applications) {
        final List<ProjectApplicationIdAttachmentFile> attachmentFiles = applications.stream()
            .flatMap(application -> application.getAttachmentFiles().stream()
                .map(attachmentFile -> new ProjectApplicationIdAttachmentFile(
                    application.getId(),
                    attachmentFile
                )))
            .toList();

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                final ProjectApplicationId applicationId = attachmentFiles.get(i).applicationId();
                final AttachmentFile attachmentFile = attachmentFiles.get(i).attachmentFile();
                ps.setLong(1, applicationId.getValue());
                ps.setString(2, attachmentFile.getUrl().getValue());
            }

            @Override
            public int getBatchSize() {
                return attachmentFiles.size();
            }
        });
    }
}
