package es.princip.getp.api.support;

import es.princip.getp.application.support.Cursor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CursorDefault {
    String value() default "cursor";
    Class<? extends Cursor> type() default Cursor.class;
}
