package ORM;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Annotations.*;
import DBComponents.DbConnection;



public class Operations<T, FK> extends OperationHelper<T, FK> {

    public Operations(){
        super();
    }
	public Operations(DbConnection db, Class<T> classToCreate, Set<FK> set) {
		super(db, classToCreate, set);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean save(T object){
        Method[] methods = classToCreate.getDeclaredMethods();		//luam fiecare metoda si construiesc 2 array-uri cu numele campurilor si valorile lor
		
		String[] fieldsQuery = new String[methods.length];
		String[] valuesQuery = new String[methods.length];
		Set<FK> setForUpdate = null;
		Boolean makeUpdateOnForeign;
		Annotation annotationForUpdate = classToCreate.getAnnotation(Table.class);
		Table infoAnnotation = (Table) annotationForUpdate;
		makeUpdateOnForeign = infoAnnotation.mainClass();
		int i = 0;
		Method setId = null;
		for(Method m : methods){
			Annotation annotationForGetId = m.getAnnotation(GetValue.class);
			Annotation annotationForSetId = m.getAnnotation(SetValue.class);
			if(annotationForSetId != null){
				SetValue infoForSetId = (SetValue) annotationForSetId;
				if(infoForSetId.id())
					setId = m;
			}
			if(annotationForGetId != null){
				GetValue info = (GetValue) annotationForGetId;
				if(!info.foreign())
				try {		
					i++;
					fieldsQuery[i] = info.name();
					valuesQuery[i] = m.invoke(object).toString();	//iau valoarea campului prin metoda getNume_Camp
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new RuntimeException("Error when extracting values to insert new row");
				}
				else{
					try {
						setForUpdate = (Set<FK>) m.invoke(object);
                        if(setForUpdate == null)
                            setForUpdate = new HashSet<>();
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
					
			}
			
			
		
		}
		
		
		return executeQueryInsert(fieldsQuery,valuesQuery,setId,object,makeUpdateOnForeign,setForUpdate);
	}

    public ArrayList<T> select(T o){
		
		query = createQuerySelect(o);
		ArrayList<T> objects = new ArrayList<T>();
		try {
			ResultSet rs = conn.select(query);
			Integer nr = rs.getMetaData().getColumnCount();

			while(rs.next()){
				T objectNew = classToCreate.newInstance();
				for(int i = 1; i <= nr; i++){
					Method method = getMethod(rs.getMetaData().getColumnName(i), classToCreate);
					Annotation annotation = method.getAnnotation(SetValue.class);
					if(annotation != null){
						method.invoke(objectNew,rs.getObject(i));
					}
				}
				objects.add(objectNew);
			}
		} catch (SQLException | NullPointerException e) {
			throw new RuntimeException("ERROR can't execute query in operation, error at ResultSet");
		} catch (SecurityException e) {
			throw new RuntimeException("Can't set value of a field, no method defined");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Can't create object");
		} catch (InstantiationException e) {
			throw new RuntimeException("Can't create new instance");
		}
		return objects;
		 
	}

    public Boolean update(T o){       //updatez campurile obiectului o
        StringBuffer query  = new StringBuffer("UPDATE ");              //creez string
        query.append(" "+tableName+ " SET ");
        String id = "";
        String idName = "";
        Method methodForUpadte = null;
        for(Field field : classToCreate.getDeclaredFields()){                  //pentru fiecare field
            field.setAccessible(true);
            if(!field.isAnnotationPresent(Foreign.class) && !field.isAnnotationPresent(Id.class)){     //verific daca nu e primary key sau camp strain
                try {
                    query.append(" " + field.getName() + " ='" + field.get(o) + "',");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can't make update, error on query string creation");
                }
            }else{
                if(field.isAnnotationPresent(Id.class)){
                    try {
                        id = field.get(o).toString();
                        idName = field.getName();
                    } catch (IllegalAccessException e) {
                       throw new RuntimeException("Error when i try to get name and value of the primary keys");
                    }
                }
              /* if(field.isAnnotationPresent(Foreign.class)){
                    // methodForUpadte = classToCreate.getDeclaredMethod("get" + )
                }  */
            }
            field.setAccessible(false);

        }

        query.delete(query.length()-1,query.length());
        query.append("  WHERE "+ idName + " ='" + id + "'");
        return conn.executeQuery(query.toString());


    }

    public Boolean delete(T o){
        StringBuffer query = new StringBuffer("DELETE FROM " + tableName + " WHERE ");
        String idName = "";
        String id = "";
        for(Field field : classToCreate.getDeclaredFields()){
            if(field.isAnnotationPresent(Id.class)){
                field.setAccessible(true);
                try {
                    id = field.get(o).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                idName = ((Column)field.getAnnotation(Column.class)).name();
                field.setAccessible(false);
            }
        }
        query.append(" "+ idName + "='" + id + "'");
        return conn.executeQuery(query.toString());


    }


}
