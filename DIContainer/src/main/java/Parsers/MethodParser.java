package Parsers;


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MethodParser{


    public Set<Method> parse(Class classToParse){
        Method[] ms = classToParse.getDeclaredMethods();
        Set<Method> methods = new HashSet<>();
        for(Method m : ms){
            methods.add(m);
        }
        return methods;
    }
}
