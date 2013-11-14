package Parsers;


import Annotations.Injection;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class FieldParser{

    FieldParser(){};

    public static Set<Field> parse(Class classToParse){
        Field[] fs = classToParse.getDeclaredFields();
        Set<Field> fields = new HashSet<>();
        for(Field f : fs){
            if(f.isAnnotationPresent(Injection.class))
                fields.add(f);
        }
        return fields;
    }
}
