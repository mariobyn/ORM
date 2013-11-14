package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using to declare references when we want to make a relation between 2 tables
 * colum - the column that will store foreign key
 * table - parent table
 * set - if we set foreign key on this table
 * foreignClass - used in parent class when we want to specifie the child class.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface References {
	String column() default "null";

	String table() default "";

	boolean set() default false;

    Class foreignClass() default Object.class;


}
