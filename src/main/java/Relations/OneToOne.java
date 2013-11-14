package Relations;
import Annotations.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import Annotations.GetValue;
import Annotations.SetValue;
import DBComponents.DbConnection;


public class OneToOne<T,FK> implements RelationChanges<FK,T>{

	public void change(Set<FK> setForUpdate, DbConnection conn, T object, Object id) {
		String queryUpdate = "Insert into ";
		ArrayList<String> fieldsQ = new ArrayList<String>();
		ArrayList<String> valuesQ = new ArrayList<String>();
		Iterator<FK> it = setForUpdate.iterator();
		while(it.hasNext()){
			FK objectForInsert = (FK) it.next();
			System.out.println(objectForInsert);
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
			System.out.println(queryUpdate);
			Object Id = conn.executeInsert(queryUpdate);
			System.out.println(Id);
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
            System.out.println(object2.getClass().getName().substring(4));
            Method methodSet = object.getClass().getMethod("set" + object2.getClass().getName().substring(4),setForUpdate.getClass());
            System.out.println(methodSet.getName());
            methodSet.invoke(object, setForUpdate);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException |InvocationTargetException | SecurityException e) {
			throw new RuntimeException("Error in OneToMany at invoke method");
		}  */
		
		
	}

}
