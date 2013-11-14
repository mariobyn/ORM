package Annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * set on a class to specify table name that will be created.
 * mainClass - if the class it's a parent class
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	String name();

	boolean mainClass() default false;
}
