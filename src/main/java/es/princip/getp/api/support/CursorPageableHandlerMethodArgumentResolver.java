package es.princip.getp.api.support;

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

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class CursorPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_CURSOR_PARAMETER = "cursor";
    private final CursorParser cursorParser;
    private final PageableHandlerMethodArgumentResolver pageableResolver;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return CursorPageable.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
        @NonNull final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        @NonNull final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        final Pageable pageable = pageableResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        if (!hasLessThanOneOrder(pageable)) {
            throw new IllegalArgumentException("CursorPageable requires exactly one sort order");
        }
        final Map<String, String> cursors = cursorParser.parse(getCursorString(parameter, webRequest));
        if (cursors.isEmpty() && pageable.getSort().isSorted()) {
            throw new IllegalArgumentException("Cursor is required");
        }
        return new CursorPageRequest(pageable, cursors);
    }

    private String getCursorString(final MethodParameter parameter, final NativeWebRequest webRequest) {
        return Optional.ofNullable(getCursorString(webRequest, DEFAULT_CURSOR_PARAMETER))
            .orElseGet(() -> {
                final CursorDefault defaults = Optional.ofNullable(parameter.getParameterAnnotation(CursorDefault.class))
                    .orElseThrow(() -> new IllegalArgumentException("CursorDefault annotation is required"));
                final String cursorParam = defaults.value();
                return getCursorString(webRequest, cursorParam);
            });
    }

    private String getCursorString(final NativeWebRequest webRequest, final String parameterName) {
        return webRequest.getParameter(parameterName);
    }

    private boolean hasLessThanOneOrder(final Pageable pageable) {
        return pageable.getSort().stream().count() <= 1;
    }
}
