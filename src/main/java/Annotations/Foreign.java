package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Foreign - used when we want to set a relation between 2 tables
 * relation - the type of relation (Example:one-to-one,one-to-many,many-to-may)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Foreign {
	String relation();

}
