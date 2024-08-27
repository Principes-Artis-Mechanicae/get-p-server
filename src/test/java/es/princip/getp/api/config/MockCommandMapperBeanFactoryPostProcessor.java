package es.princip.getp.api.config;

import es.princip.getp.api.controller.common.mapper.CommandMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.platform.commons.util.ClassFilter;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import static org.mockito.Mockito.mock;

public class MockCommandMapperBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(@NotNull final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final ClassFilter classFilter = ClassFilter.of(clazz -> clazz.isAnnotationPresent(CommandMapper.class));
        ReflectionUtils.findAllClassesInPackage("es.princip.getp.api", classFilter)
            .forEach(clazz -> {
                beanFactory.registerSingleton(clazz.getSimpleName(), mock(clazz));
            });
    }
}