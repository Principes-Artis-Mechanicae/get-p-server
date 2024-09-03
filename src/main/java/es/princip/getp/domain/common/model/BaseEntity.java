package es.princip.getp.domain.common.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity extends BaseModel {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected BaseEntity() {
        this.createdAt = null;
        this.updatedAt = null;
    }

    protected BaseEntity(final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
