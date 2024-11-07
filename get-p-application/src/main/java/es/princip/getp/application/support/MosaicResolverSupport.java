package es.princip.getp.application.support;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

public abstract class MosaicResolverSupport {

    private static final String RESTRICTED_ACCESS = "restricted.access";
    private final MessageSource messageSource;

    protected MosaicResolverSupport(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getRestrictedAccessMessage() {
        return messageSource.getMessage(RESTRICTED_ACCESS, null, Locale.getDefault());
    }

    protected String mosaicMessage(final String original) {
        if (original == null) {
            return null;
        }

        final int originalLength = original.length();
        final String message = getRestrictedAccessMessage();
        final int messageLength = message.length();

        final StringBuilder sb = new StringBuilder(originalLength);
        for (int i = 0; i < originalLength; i++) {
            sb.append(message.charAt(i % messageLength));
        }

        return sb.toString();
    }

    protected List<String> mosaicMessage(final List<String> original) {
        if (original == null) {
            return null;
        }

        return original.stream()
            .map(this::mosaicMessage)
            .toList();
    }
}