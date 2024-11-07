package es.princip.getp.api.docs;

import com.epages.restdocs.apispec.EnumFields;

public class EnumDescriptor {

    public static EnumFields fieldWithEnum(final Class<?> clazz) {
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("The class must be an enum type.");
        }
        return new EnumFields(clazz);
    }
}
