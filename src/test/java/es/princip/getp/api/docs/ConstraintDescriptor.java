package es.princip.getp.api.docs;

import org.springframework.restdocs.constraints.Constraint;
import org.springframework.restdocs.constraints.ConstraintDescriptionResolver;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.constraints.ValidatorConstraintResolver;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ConstraintDescriptor {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/validation");
    private static final ValidatorConstraintResolver constraintResolver = new ValidatorConstraintResolver();
    private static final ConstraintDescriptionResolver descriptionResolver = new ResourceBundleConstraintDescriptionResolver();

    private static final String CONSTRAINT_KEY = "constraints";
    private static final String OAS_CONSTRAINT_KEY = "validationConstraints";

    public static <T> FieldDescriptor fieldWithConstraint(final String path, final Class<T> clazz) {
        final List<Constraint> constraints = getConstraintsForOAS(path, clazz);
        final String message = getConstraintsForDocs(path, clazz);
        return fieldWithPath(path)
            .attributes(key(CONSTRAINT_KEY).value(message))
            .attributes(key(OAS_CONSTRAINT_KEY).value(constraints));
    }

    private static List<Constraint> getConstraintsForOAS(final String path, final Class<?> clazz) {
        final String[] properties = path.split("\\.");
        final String property = properties[properties.length - 1];
        return constraintResolver.resolveForProperty(property, clazz).stream()
            .map(constraint -> {
                final Map<String, Object> configuration = new HashMap<>(constraint.getConfiguration());
                configuration.put("message", getConstraintMessage(constraint));
                return new Constraint(constraint.getName(), configuration);
            })
            .collect(Collectors.toList());
    }

    private static String getConstraintsForDocs(final String path, final Class<?> clazz) {
        final String[] properties = path.split("\\.");
        final String property = properties[properties.length - 1];
        return constraintResolver.resolveForProperty(property, clazz).stream()
            .map(ConstraintDescriptor::getConstraintMessage)
            .collect(Collectors.joining(", "));
    }

    private static String getConstraintMessage(final Constraint constraint) {
        try {
            return getConstraintMessageFromResourceBundle(constraint);
        } catch (MissingResourceException e) {
            return getDefaultConstraintMessage(constraint);
        }
    }

    private static String getConstraintMessageFromResourceBundle(final Constraint constraint)
        throws MissingResourceException {
        final String key = ((String) constraint.getConfiguration().get("message"))
            .replace("{", "")
            .replace("}", "");
        return resourceBundle.getString(key);
    }

    private static String getDefaultConstraintMessage(final Constraint constraint) {
        return descriptionResolver.resolveDescription(constraint);
    }
}
