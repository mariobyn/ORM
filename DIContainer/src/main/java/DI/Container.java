package DI;

import Annotations.Injection;
import Parsers.ClassParser;
import Parsers.FieldParser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Container {
    protected static Container instance;
    protected HashMap<Class<?>, Class<?>> classMap;
    private ObjectGraph graph;
    private InstancesMap instancesMap = new InstancesMap();

    public Container(){
        this.classMap = new HashMap<>();
    }

    /*public static Container getInstance(){

        if(Container.instance == null){
            Container.instance = new Container();
        }

        return Container.instance;

    }  */
    public void ContainerStart(){
        graph = new ObjectGraph();
        //ClassParser parse = new ClassParser();
        Set<?> classes = ClassParser.parse("ObjectClasses");
       // System.out.println(classes.size());
        Iterator cls = classes.iterator();
        Class auxClass = null;
        Set<Field> fields;
        while(cls.hasNext()){
             auxClass = (Class)cls.next();
             fields= FieldParser.parse(auxClass);

           // instancesMap.setInstance(auxClass);
             Iterator it = fields.iterator();
             if(it.hasNext()){
                 if(auxClass != null)
                     instancesMap.setInstance(auxClass);
             while(it.hasNext()){
                    //System.out.println(auxClass);
                 Field obj = (Field)it.next();
                 obj.setAccessible(true);
                    graph.addNeighbor(auxClass,(Class)(obj.getType()));
                    instancesMap.setInstance((Class)(obj.getType()));
                 try {
                     obj.set(instancesMap.getInstance((Class) obj.getType()),instancesMap.getInstance((Class) obj.getType()));
                 } catch (Exception e) {
                     throw new RuntimeException("Error can't create instance");
                 }
                 //System.out.println((Class)(((Field)obj).getType()));
             }
             }else {
                 graph.addNeighbor(auxClass,null);
                 instancesMap.setInstance(auxClass);
             }


            /*fields =  auxClass.getDeclaredFields();

             for(Field field : fields){
                 //System.out.println(field.getName());
                 if(field.isAnnotationPresent(Injection.class)){
                     System.out.println(field);
                 }
             }     */
           // System.out.println(auxClass.getName().ge);
        }
        List list = graph.TopologicalSort();

    }
}
