package es.princip.getp.api.support;

import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
class CursorPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final CursorHandlerMethodArgumentResolver cursorResolver;
    private final PageableHandlerMethodArgumentResolver pageableResolver;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return CursorPageable.class.equals(parameter.getParameterType()) &&
            parameter.hasParameterAnnotation(CursorDefault.class);
    }

    @Override
    public Object resolveArgument(
        @NonNull final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        @NonNull final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        final Pageable pageable = pageableResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        final Cursor cursor = cursorResolver.resolveCursor(parameter, mavContainer, webRequest, binderFactory);
        return new CursorPageRequest<>(pageable, cursor);
    }
}
