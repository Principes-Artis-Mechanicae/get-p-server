package es.princip.getp.infra.exception;

/**
 * EntityAlreadyExistsException는 이미 엔티티가 존재할 때 발생하는 예외다. 엔티티를 단일로 관리하는 경우에 사용한다.
 */
public class EntityAlreadyExistsException extends BusinessLogicException {

    public EntityAlreadyExistsException(final String message) {
        super(message);
    }
}