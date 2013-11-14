package DI;


import java.util.HashMap;

public class InstancesMap {

    private HashMap<Class<?>,Object> instances;

    InstancesMap(){
        instances = new HashMap();
    }

    public void setInstance(Class<?> mainClass){
        //System.out.println(mainClass);
        if(!instances.containsKey(mainClass)){
            try {
                instances.put(mainClass,mainClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
               throw  new RuntimeException("Can't add instance for class " + mainClass.getName());
            }
        }
    }

    public Object getInstance(Class<?> mainClass){
        if(!instances.containsKey(mainClass)){
            setInstance(mainClass);
        }

        return instances.get(mainClass);
    }

}
