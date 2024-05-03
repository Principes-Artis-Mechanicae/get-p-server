package es.princip.getp.global.support;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ErrorCodeFields extends AbstractFieldsSnippet {

    private ErrorCodeFields(
        final List<FieldDescriptor> descriptors
    ) {
        super("error-code", descriptors, null, true);
    }

    @Override
    protected MediaType getContentType(final Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(final Operation operation) throws IOException {
        return operation.getResponse().getContent();
    }

    public static ErrorCodeFields errorCodeFields(ErrorCode[] errorCodes) {
        List<FieldDescriptor> descriptors = Arrays.stream(errorCodes).map(errorCode ->
            fieldWithPath(errorCode.description().code()).attributes(
                key("code").value(errorCode.description().code()),
                key("message").value(errorCode.description().message()),
                key("status").value(errorCode.status().value())
            )).toList();
        return new ErrorCodeFields(descriptors);
    }
}
