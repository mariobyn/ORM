package Parsers;


import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;


import java.util.Set;

public class ClassParser{

    public ClassParser(){};

    public static Set<Class<?>> parse(String packageName){


        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forPackage(packageName))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

        return reflections.getSubTypesOf(Object.class);


    }
}
