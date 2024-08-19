package es.princip.getp.infra.docs;

import org.springframework.restdocs.constraints.Constraint;
import org.springframework.restdocs.constraints.ConstraintDescriptionResolver;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.constraints.ValidatorConstraintResolver;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class FieldDescriptorHelper {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/validation");
    private static final String DELIMITER = " ";
    private static final ValidatorConstraintResolver constraintResolver = new ValidatorConstraintResolver();
    private static final ConstraintDescriptionResolver descriptionResolver = new ResourceBundleConstraintDescriptionResolver();

    public static FieldDescriptor getDescriptor(String path, String description) {
        return getDescriptor(path, description, "");
    }

    public static FieldDescriptor getDescriptor(String path, String description, String constraint) {
        return fieldWithPath(path)
            .description(description)
            .attributes(key("constraints").value(constraint));
    }

    public static <T> FieldDescriptor getDescriptor(String path, String description, Class<T> clazz) {
        String[] properties = path.split("\\.");
        String property = properties[properties.length - 1];
        String constraintMessages = constraintResolver.resolveForProperty(property, clazz).stream()
            .map(FieldDescriptorHelper::getConstraintMessage)
            .collect(Collectors.joining(DELIMITER));
        return getDescriptor(path, description, constraintMessages);
    }

    private static String getConstraintMessage(Constraint constraint) {
        try {
            return getConstraintMessageFromResourceBundle(constraint);
        } catch (MissingResourceException e) {
            return getDefaultConstraintMessage(constraint);
        }
    }

    private static String getConstraintMessageFromResourceBundle(
        Constraint constraint
    ) throws MissingResourceException {
        String key = ((String) constraint.getConfiguration().get("message"))
            .replace("{", "")
            .replace("}", "");
        return resourceBundle.getString(key);
    }

    private static String getDefaultConstraintMessage(Constraint constraint) {
        return descriptionResolver.resolveDescription(constraint);
    }
}
