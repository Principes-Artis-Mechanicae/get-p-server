package es.princip.getp.application.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MosaicFactory {

    private final List<MosaicResolver<?>> mosaicResolvers;

    @SuppressWarnings("unchecked")
    public <T> T mosaic(final T response) {
        final Class<?> clazz = response.getClass();
        final MosaicResolver<T> resolver = (MosaicResolver<T>) mosaicResolvers.stream()
            .filter(res -> res.supports(clazz))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 클래스에 대한 MosaicResolver가 없습니다."));
        return resolver.resolve(response);
    }
}