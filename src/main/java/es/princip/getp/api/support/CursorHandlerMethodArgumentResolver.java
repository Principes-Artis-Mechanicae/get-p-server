package es.princip.getp.api.support;

import es.princip.getp.application.support.Cursor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
class CursorHandlerMethodArgumentResolver extends CursorHandlerMethodArgumentResolverSupport {

    private final CursorParser cursorParser;

    @Override
    public Cursor resolveCursor(
        @NonNull final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        @NonNull final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        final Class<? extends Cursor> cursorType = getCursorType(parameter);
        final String cursorString = getCursorString(webRequest, parameter);
        return cursorParser.parse(cursorType, cursorString);
    }
}
