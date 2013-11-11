package Annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	String name();
	String Kind();
	String ConstraintP() default ""; 	//For primary key
	String ConstraintF() default "";	//For foreign key
	String Auto_Increment() default ""; //For Auto_Increment
	String NULL() default ""; //If is null
	String Default() default "";	//Default Value
	

}
