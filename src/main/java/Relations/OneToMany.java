package Relations;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Annotations.GetValue;
import Annotations.References;
import Annotations.SetValue;
import Annotations.Table;
import DBComponents.DbConnection;


public class OneToMany<T,FK> implements RelationChanges<FK,T>{
	
	public void change(Set<FK> setForUpdate, DbConnection conn, T object, Object id) {
		String queryUpdate = null;
		ArrayList<String> fieldsQ = new ArrayList<>();
		ArrayList<String> valuesQ = new ArrayList<>();
        for(Field field : object.getClass().getDeclaredFields()){        //caut field-ul care tine minte set-ul de obiecte straine si il fac null pentru a nu incarca obiectul curent.
            if(field.isAnnotationPresent(References.class)){
                  field.setAccessible(true);
                try {
                    field.set(object,new HashSet<>());
                } catch (IllegalAccessException e) {
                   new RuntimeException("Error can't make foreign field null");
                }

            }
        }
		Iterator<FK> it = setForUpdate.iterator();
		while(it.hasNext()){
             queryUpdate = "Insert into ";
            fieldsQ.clear();
            valuesQ.clear();
			FK objectForInsert = (FK) it.next();
			//System.out.println(objectForInsert);
			queryUpdate += " " + objectForInsert.getClass().getAnnotation(Table.class).name() + " (";
			Method[] methods = objectForInsert.getClass().getDeclaredMethods();
			Method forSetId = null;
			for(Method method : methods){
				Annotation annForSet = method.getAnnotation(SetValue.class);
				Annotation annInfoKey = method.getAnnotation(GetValue.class);
				GetValue infoKey = (GetValue) annInfoKey;
				if(infoKey != null && !infoKey.id()){
					try {
						Object obj = method.invoke(objectForInsert);
							fieldsQ.add(infoKey.name());
							if(obj == null){
								if(infoKey.foreign())
									valuesQ.add(id.toString());
								else
									valuesQ.add("");
							}
							else
								valuesQ.add(obj.toString());
						
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				SetValue infoForSet = (SetValue) annForSet;
				if(infoForSet != null && infoForSet.id()){
					forSetId = method;
				}
			}
			Iterator<String> itForQuery = fieldsQ.iterator();
			while(itForQuery.hasNext()){
				queryUpdate += itForQuery.next() + ",";
			}
			
			queryUpdate = queryUpdate.substring(0, queryUpdate.length()-1) + ") VALUES (";
			itForQuery = valuesQ.iterator();
			while(itForQuery.hasNext()){
				queryUpdate += "'" + itForQuery.next() + "',";
			}
			queryUpdate = queryUpdate.substring(0, queryUpdate.length()-1) + ")";
			Object Id = conn.executeInsert(queryUpdate);
			try {
				forSetId.invoke(objectForInsert, Id);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


		/*try {
			FK object2 = setForUpdate.iterator().next();
			Method methodSet = object.getClass().getMethod("set" + object2.getClass().getName().substring(4),setForUpdate.getClass());
			System.out.println(methodSet.getName());
            methodSet.invoke(object, setForUpdate);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException |InvocationTargetException | SecurityException e) {
			e.printStackTrace();
            throw new RuntimeException("Error in OneToMany at invoke method");
		}   */
		
		
	}

}
