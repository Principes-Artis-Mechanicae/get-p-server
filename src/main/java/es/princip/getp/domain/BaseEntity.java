package es.princip.getp.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity extends BaseModel {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
