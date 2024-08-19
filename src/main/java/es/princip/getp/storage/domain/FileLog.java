package es.princip.getp.storage.domain;

import es.princip.getp.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Table(name = "file_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileLog extends BaseTimeEntity {
    
    @Id
    @Getter
    @Column(name = "file_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "file_name")
    private String fileName;

    public FileLog(final String fileName) {
        this.fileName = fileName;
    }

    public Path getFilePath() {
        return Paths.get(String.valueOf(id)).resolve(fileName);
    }
}
