package es.princip.getp.api.config;

import jakarta.validation.constraints.NotNull;
import org.junit.platform.commons.util.ClassFilter;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import static org.mockito.Mockito.mock;

public class MockServiceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(@NotNull final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final ClassFilter classFilter = ClassFilter.of(clazz -> clazz.isAnnotationPresent(Service.class));
        ReflectionUtils.findAllClassesInPackage("es.princip.getp", classFilter)
            .forEach(clazz -> {
                beanFactory.registerSingleton(clazz.getSimpleName(), mock(clazz));
            });
    }
}