package DI;

import java.util.*;

public class ObjectGraph {

    private HashMap<Class<?>,List<Class<?>>> graph;

    ObjectGraph(){
         graph = new HashMap<>();
    }

    public void addNeighbor(Class<?> mainClass, Class<?> neighbor){
          if(graph.containsKey(mainClass)){
              if(!graph.get(mainClass).contains(neighbor))
                graph.get(mainClass).add(neighbor);
          }else{
              List<Class<?>> list = new ArrayList<>();
              list.add(neighbor);
              graph.put(mainClass,list);
          }
    }

    public List<Class<?>> getNeighbors(Class<?> mainClass){
        if(graph.containsKey(mainClass))
            return graph.get(mainClass);
        else
            return new ArrayList<>();
    }



    public List<Class<?>> TopologicalSort(){
        List<Object> topological = new ArrayList<>();
        Object[] keySet = graph.keySet().toArray();
         /*for(int i = 0;i<keySet.length;i++){
             System.out.println(keySet[i]);
         }  */

        /*HashMap<Class<?>,List<Class<?>>> hm = new HashMap<>(graph);
       //System.out.println(keySet.length);
         int i = 0;
        int j = 0;
        while(hm.size() != 0){
            j++;
            if(j == 5)
                break;
            if(i == keySet.length)
                i=0;
            System.out.println(keySet[i]);
            if(hm.containsKey(keySet[i])){
                if(hm.get(keySet[i]).isEmpty()){
                    System.out.println(hm.get(keySet[i]));
                   topological.add(keySet[i]);
                    hm.get(keySet[i]).remove()
                    hm.remove(keySet[i]);
                }

            }
            i++;
        }
        Iterator it =  topological.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }     */
        //System.out.println(topological.size());
        return null;
    }


}
