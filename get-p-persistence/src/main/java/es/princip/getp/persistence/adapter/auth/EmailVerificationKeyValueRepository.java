package es.princip.getp.persistence.adapter.auth;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface EmailVerificationKeyValueRepository extends KeyValueRepository<EmailVerificationRedisEntity, String> {

}