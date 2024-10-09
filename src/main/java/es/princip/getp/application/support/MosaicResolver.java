package es.princip.getp.application.support;

public interface MosaicResolver<T> {

    boolean supports(Class<?> clazz);
    T resolve(T response);
}
