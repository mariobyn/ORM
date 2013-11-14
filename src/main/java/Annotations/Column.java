package Annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * name - name of database field
 * Kind - data type that will be stored in this field
 * ConstraintP - if this field will be primary key
 * Auto_Increment - auto increment
 * Null - if will be null or not
 * Default - if we want to set a default value
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	String name();
	String Kind();
	String ConstraintP() default ""; 	//For primary key
	String Auto_Increment() default ""; //For Auto_Increment
	String NULL() default ""; //If is null
	String Default() default "";	//Default Value
	

}
